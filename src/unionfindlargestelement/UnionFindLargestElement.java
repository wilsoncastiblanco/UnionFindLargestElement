/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unionfindlargestelement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author wilsoncastiblanco
 */
public class UnionFindLargestElement {
    
    public static class WeightedUnionFind {

        private int[] ids;
        private int[] size;
        private int roots;
        private int N;

        public WeightedUnionFind(int n) {
            this.N = n;
            this.roots = n;
            this.ids = new int[n];
            this.size = new int[n];

            for (int i = 0; i < n; i++) {
                ids[i] = i;
            }
            
            Arrays.fill(size, 1);
        }

        public int find(int index) {
            int id = this.ids[index];
            int biggestNumber = -1;
            for(int i = 0; i < ids.length; i++){
                if(this.ids[i] == id){
                    if(i > biggestNumber){
                        biggestNumber = i;
                    }
                }
            }
            return biggestNumber == -1 ? index : biggestNumber;
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);

            if (rootP == rootQ) {
                return;
            }

            if (size[rootP] < size[rootQ]) {
                ids[rootP] = rootQ;
                size[rootQ] += ids[rootP];

            } else {
                ids[rootQ] = rootP;
                size[rootP] += ids[rootQ];
            }

            this.roots--;
        }

        public boolean allSitesConnected() {
            return this.roots == 1;
        }

        public void validateEntries(int index) {
            if (index < 0 || index >= N) {
                throw new IllegalArgumentException(String.format("Entry %d must be more than 0 and less than %d", index, N));
            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("network.in"));
        String linea = bufferedReader.readLine();
        if (linea != null) {
            int n = Integer.parseInt(linea);
            WeightedUnionFind weightedUnionFind = new WeightedUnionFind(n);
            linea = bufferedReader.readLine();

            while (linea != null) {
                String[] data = linea.split(" ");

                int p = Integer.parseInt(data[0]);
                int q = Integer.parseInt(data[1]);

                if (weightedUnionFind.connected(p, q)) {
                    linea = bufferedReader.readLine();
                    continue;
                }
                weightedUnionFind.union(p, q);
                
                System.out.println("P["+p+"] Q["+q+"]");

                linea = bufferedReader.readLine();

            }
            System.out.println("Terminó " + weightedUnionFind.allSitesConnected());

        }

    }
    
}
