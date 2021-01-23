package com.booking.recommendation;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CollaborativeFiltering {
    private String realPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
    private String pathFolder = realPath.substring(realPath.lastIndexOf("C:"), realPath.lastIndexOf("/booking"))
            + "/publics/booking/";

    public static double[][] read(String s) throws NumberFormatException, IOException {

        double[][] matrixData = new double[20][32];
        File file = new File(s);

        try {
            BufferedReader readFile = new BufferedReader(new FileReader(file));
            String line;
            int row = 0;
            while ((line = readFile.readLine()) != null) {
                String[] ratings = line.split(",");
                for (int column = 0; column < ratings.length; column++) {
                    matrixData[row][column] = Double.valueOf(ratings[column]);
                }
                row++;
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return matrixData;
    }

//    public static void main(String[] args) {
//        BufferedWriter bw = null;
//        FileWriter fw = null;
//        try {
//            File fileDataTraining = new File("C:\\Users\\ddung\\Desktop\\data_training.txt");
//            // kiểm tra nếu file chưa có thì tạo file mới
//            if (!fileDataTraining.exists()) {
//                fileDataTraining.createNewFile();
//            }
//            // true = append file
//            fw = new FileWriter(fileDataTraining.getAbsoluteFile(), true);
//            bw = new BufferedWriter(fw);
//            String record = "\n";
//            for(int i = 0; i < 15; i++) {
//                for (int j = 0; j < 32; j++) {
//                    record = record + "," + ThreadLocalRandom.current().nextInt(-1, 6);
//                }
//                record = record + "\n";
//            }
//            bw.write(record);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (bw != null)
//                    bw.close();
//                if (fw != null)
//                    fw.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }

    public double[] recommendation(long userId) throws IOException {
        // Doc data training tu file
        double[][] matrixData = read(pathFolder + "data_training_collaborative_filtering.txt");
        RealMatrix m = MatrixUtils.createRealMatrix(matrixData);
        //displayMatrix(matrixData, m.getRowDimension(), m.getColumnDimension());
        if (userId > m.getColumnDimension()) {
            double[] columnVector = new double[m.getRowDimension()];
            for (int column = 0; column < m.getRowDimension(); column++) {
                columnVector[column] = -1;
            }
            m.setColumn(m.getColumnDimension() + 1, columnVector);
        }
        //Tinh trung binh cong moi cot (column ~ user - row ~ item)
        double[] avgOfColumns = new double[m.getColumnDimension()];
        for (int column = 0; column < m.getColumnDimension(); column++) {
            try {
                avgOfColumns[column] = Arrays.stream(m.getColumn(column)).filter(value -> value >= 0).average().getAsDouble();
            } catch (NoSuchElementException e) {
                avgOfColumns[column] = 0;
            }
        }

        //Moi phan tu se tru di gia tri trung binh cong cua cot tuong ung
        for (int column = 0; column < m.getColumnDimension(); column++) {
            for (int row = 0; row < m.getRowDimension(); row++) {
                if (m.getEntry(row, column) < 0) {
                    m.setEntry(row, column, 0);
                } else {
                    m.setEntry(row, column, m.getEntry(row, column) - avgOfColumns[column]);
                }
            }
        }

        //displayMatrix(m.getData(), (int) m.getColumnDimension(), (int) m.getColumnDimension());

        //Tinh do tuong quan giua 2 user theo cong thuc cosine 2 vector
        double[][] similarMatrix = new double[m.getColumnDimension()][m.getColumnDimension()];
        for (int column = 0; column < m.getColumnDimension(); column++) {
            for (int row = 0; row < m.getColumnDimension(); row++) {
                if (row == column) {
                    similarMatrix[column][row] = 1;
                } else {
                    similarMatrix[column][row] = cosineSimilarity(m.getColumn(column), m.getColumn(row));
                }
            }
        }
        RealMatrix realMatrixOfSimilarMatrix = MatrixUtils.createRealMatrix(similarMatrix);

        //System.out.println("Similar Matrix: ");
        //displayMatrix(similarMatrix, (int) m.getColumnDimension(), (int) m.getColumnDimension());

        //Cac item da duoc danh gia boi user nao
        int numberOfNearestNeighbors = 2;
        HashMap<Integer, List<Integer>> ratedPoint = new HashMap<>(); //item = [users]
        for (int row = 0; row < m.getRowDimension(); row++) {
            List<Integer> userRated = new ArrayList<>();
            for (int col = 0; col < m.getColumnDimension(); col++) {
                if (m.getEntry(row, col) != 0) {
                    userRated.add(col);
                }
            }
            ratedPoint.put(row, userRated);
        }
        //System.out.println(ratedPoint);

        //Dien gia tri thich hop vao nhung item chua dc danh gia
        for (int row = 0; row < m.getRowDimension(); row++) {
            for (int column = 0; column < m.getColumnDimension(); column++) {
                if (m.getEntry(row, column) == 0) {
                    List<Integer> nearestNeighbors = ratedPoint.get(row);
                    Map<Integer, Double> similarities = new HashMap<>();
                    for (int nearestNeighbor : nearestNeighbors) {
                        if (realMatrixOfSimilarMatrix.getEntry(row, nearestNeighbor) != 0) {
                            similarities.put(nearestNeighbor, realMatrixOfSimilarMatrix.getEntry(row, nearestNeighbor));
                        }
                    }
                    similarities = sortByValue(similarities);
                    Map<Integer, Double> normalizedRatings = new HashMap<>();
                    List<Integer> keys = new ArrayList<>();
                    keys.addAll(similarities.keySet());
                    for (int i = 0; i < numberOfNearestNeighbors; i++) {
                        normalizedRatings.put(keys.get(i), m.getEntry(row, keys.get(i)));
                    }

                    double predict = (similarities.get(keys.get(0)) * normalizedRatings.get(keys.get(0)) + similarities.get(keys.get(1)) * normalizedRatings.get(keys.get(1))) / (Math.abs(similarities.get(keys.get(0))) + Math.abs(similarities.get(keys.get(1))));

                    m.setEntry(row, column, predict);
                }
            }
        }

        //System.out.println("Full Matrix: ");
        //displayMatrix(m.getData(), m.getRowDimension(), m.getColumnDimension());

        return m.getColumn((int) userId);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        if (dotProduct == 0d) {
            return 0;
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));

    }

    public static void displayMatrix(double[][] matrix, int _row, int _column) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (int row = 0; row < _row; row++) {
            for (int column = 0; column < _column; column++) {
                System.out.print(Double.valueOf(formatter.format(matrix[row][column])) + "\t");
            }
            System.out.println();
        }
    }
}
