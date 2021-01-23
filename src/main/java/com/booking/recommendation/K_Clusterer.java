package com.booking.recommendation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class K_Clusterer extends ReadDataset {

    private String realPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
    private String pathFolder = realPath.substring(realPath.lastIndexOf("C:"), realPath.lastIndexOf("/booking"))
            + "/publics/booking/";

    public K_Clusterer() {
        // TODO Auto-generated constructor stub
    }

    //main method
    public List<Double> recommender(double[] features) throws IOException {
        int numberOfCluster = 2;
        List<Double> result = new ArrayList<>();
        ReadDataset r1 = new ReadDataset();
        r1.features.clear();
        String file = pathFolder + "data_training.txt";
        r1.read(file); //load data
        int max_iterations = 100;
        int distance = 1;
        //Hashmap to store centroids with index
        Map<Integer, double[]> centroids = new HashMap<>();
        // calculating initial centroids
        double[] x1 = new double[numberOfFeatures];
        int r = 0;
        for (int i = 0; i < numberOfCluster; i++) {

            x1 = r1.features.get(r++);
            centroids.put(i, x1);

        }
        //Hashmap for finding cluster indexes
        Map<double[], Integer> clusters = new HashMap<>();
        clusters = kmeans(r1.features, distance, centroids, numberOfCluster);
        double db[] = new double[numberOfFeatures];
        //reassigning to new clusters
        for (int i = 0; i < max_iterations; i++) {
            for (int j = 0; j < numberOfCluster; j++) {
                List<double[]> list = new ArrayList<>();
                for (double[] key : clusters.keySet()) {
                    if (clusters.get(key) == j) {
                        list.add(key);
                    }
                }
                db = centroidCalculator(list);
                centroids.put(j, db);

            }
            clusters.clear();
            clusters = kmeans(r1.features, distance, centroids, numberOfCluster);
        }

        int cluster = predict(features, centroids, numberOfCluster);
        for (double[] key : clusters.keySet()) {
            StringBuilder record = new StringBuilder();
            record.append("\n");
            if (clusters.get(key) == cluster) {
                Iterator<double[]> itr = r1.features.iterator();
                Iterator<String> sitr = r1.label.iterator();
                while (itr.hasNext()) {
                    double[] feature = itr.next();
                    String label = sitr.next();
                    if (Arrays.equals(feature, key)) {
                        for (int i = 0; i < key.length; i++) {
                            record.append(key[i] + ",");
                        }
                        record.append(label);
                        result.add(Double.valueOf(label));
                    }
                }
//                BufferedWriter bw = null;
//                FileWriter fw = null;
//                try {
//                    File fileDataTraining = new File(file);
//                    // kiểm tra nếu file chưa có thì tạo file mới
//                    if (!fileDataTraining.exists()) {
//                        fileDataTraining.createNewFile();
//                    }
//                    // true = append file
//                    fw = new FileWriter(fileDataTraining.getAbsoluteFile(), true);
//                    bw = new BufferedWriter(fw);
//                    bw.write(record.toString());
//                    System.out.println("Xong");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        if (bw != null)
//                            bw.close();
//                        if (fw != null)
//                            fw.close();
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        //recommender(new double[]{1, 63, 680, 10542, 2, 4, 2, 3, 2, 1, 5000.0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 2});
    }

    //method to calculate centroids
    public static double[] centroidCalculator(List<double[]> a) {

        int count = 0;
        //double x[] = new double[ReadDataset.numberOfFeatures];
        double sum = 0.0;
        double[] centroids = new double[ReadDataset.numberOfFeatures];
        for (int i = 0; i < ReadDataset.numberOfFeatures; i++) {
            sum = 0.0;
            count = 0;
            for (double[] x : a) {
                count++;
                sum = sum + x[i];
            }
            centroids[i] = sum / count;
        }
        return centroids;

    }

    //method for putting features to clusters and reassignment of clusters.
    public static Map<double[], Integer> kmeans(List<double[]> features, int distance, Map<Integer, double[]> centroids, int k) {
        Map<double[], Integer> clusters = new HashMap<>();
        int k1 = 0;
        double dist = 0.0;
        for (double[] x : features) {
            double minimum = 999999.0;
            for (int j = 0; j < k; j++) {
                if (distance == 1) {
                    dist = Distance.eucledianDistance(centroids.get(j), x);
                } else if (distance == 2) {
                    dist = Distance.manhattanDistance(centroids.get(j), x);
                }
                if (dist < minimum) {
                    minimum = dist;
                    k1 = j;
                }

            }
            clusters.put(x, k1);
        }

        return clusters;

    }

    //method for putting features to clusters and reassignment of clusters.
    public static int predict(double[] exam, Map<Integer, double[]> centroids, int k) {
        int k1 = 0;
        double dist = 0.0;
        double minimum = 999999.0;
        for (int j = 0; j < k; j++) {
            dist = Distance.eucledianDistance(centroids.get(j), exam);
            if (dist < minimum) {
                minimum = dist;
                k1 = j;
            }
        }
        return k1;
    }
}
