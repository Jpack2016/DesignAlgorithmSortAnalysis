package com.jpack2012.designalgoclass;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {

        //constants given in assignment notes
        int ns = 1000;
        int nf = 20000;
        int o = 1000;
        int m = 10;

        //input array (which is an array of arrays)
        int[][] inputArray = new int[m][nf];

        //time arrays for time as each algorithm is ran
        long[][] timeAlg1 = new long[m][2*m];
        long[][] timeAlg2 = new long[m][2*m];
        long[][] timeAlg3 = new long[m][2*m];

        //time arrays for average time
        long[] alg1avg = new long[2*m];
        long[] alg2avg = new long[2*m];
        long[] alg3avg = new long[2*m];

        //For loop for creating random Elements
        for(int i = 0;i<m;i++){
            for(int j = 0; j < nf; j++){
                //Max is +1 since the upper bound of the random is exclusive, https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ThreadLocalRandom.html#nextInt-int-int-
                inputArray[i][j] = ThreadLocalRandom.current().nextInt(0, 32768);
            }
        }

        //int to keep track of iteration loops, starting from 0 to 19 (20 iterations), instead of taking elements at 1000 or 2000 or 3000, etc
        int loopnum = 0;

        //Computing Alg1 measurements
        for(int n=ns; n<=nf; n=n+o){
            for(int i=0; i<m; i++){
                //Copies array elements from 0 to 999, 1000 elements.
                int[] outputArray = Arrays.copyOfRange(inputArray[i],0,n);
                long timeBegan = System.nanoTime(); //Choices are Nano or Milli, but since initial run times were so small in milli I switched to nano.
                insertSort(outputArray);
                long diff = System.nanoTime()- timeBegan;
                timeAlg1[i][loopnum] = diff/1000;//convert nano to micro
            }
            alg1avg[loopnum] = (timeAlg1[0][loopnum] + timeAlg1[1][loopnum] + timeAlg1[2][loopnum] + timeAlg1[3][loopnum] + timeAlg1[4][loopnum] +
                    timeAlg1[5][loopnum] + timeAlg1[6][loopnum] + timeAlg1[7][loopnum] + timeAlg1[8][loopnum] + timeAlg1[9][loopnum])/m;
            loopnum++;
        }

        loopnum = 0;
        //Computing Alg2 measurements
        for(int n=ns; n<=nf; n=n+o){
            for(int i=0; i<m; i++){
                int[] outputArray = Arrays.copyOfRange(inputArray[i],0,n);
                long timeBegan = System.nanoTime();
                heapSort(outputArray);
                long timeEnd = System.nanoTime();
                long diff = timeEnd - timeBegan;
                timeAlg2[i][loopnum] = diff/1000;//convert nano to micro
            }

            alg2avg[loopnum] = (timeAlg2[0][loopnum] + timeAlg2[1][loopnum] + timeAlg2[2][loopnum] + timeAlg2[3][loopnum] + timeAlg2[4][loopnum] +
                    timeAlg2[5][loopnum] + timeAlg2[6][loopnum] + timeAlg2[7][loopnum] + timeAlg2[8][loopnum] + timeAlg2[9][loopnum])/m;
            loopnum++;
        }

        loopnum = 0;
        //Computing Alg3 measurements
        for(int n=ns; n<=nf; n=n+o){
            for(int i=0; i<m; i++){
                int[] outputArray = Arrays.copyOfRange(inputArray[i],0,n);
                long timeBegan = System.nanoTime();
                quickSort(outputArray,0,outputArray.length-1);//Initial call according to notes.
                long timeEnd = System.nanoTime();
                long diff = timeEnd - timeBegan;
                timeAlg3[i][loopnum] = diff/1000;//convert nano to micro
            }

            alg3avg[loopnum] = (timeAlg3[0][loopnum] + timeAlg3[1][loopnum] + timeAlg3[2][loopnum] + timeAlg3[3][loopnum] + timeAlg3[4][loopnum] +
                    timeAlg3[5][loopnum] + timeAlg3[6][loopnum] + timeAlg3[7][loopnum] + timeAlg3[8][loopnum] + timeAlg3[9][loopnum])/m;
            loopnum++;
        }

        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-\n");
        System.out.println("Insertion Sort Run Times!\n");
	    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-\n");
	    loopnum = 1000;
	    for(int a=0;a<alg1avg.length;a++){
		    System.out.println("Run Average For " + loopnum + " Elements: " + alg1avg[a]);
		    loopnum = loopnum + 1000;
	    }
	    System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-\n");

	    System.out.println("Heap Sort Run Times!\n");
	    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-\n");
	    loopnum = 1000;
	    for(int a=0;a<alg2avg.length;a++){
		    System.out.println("Run Average For " + loopnum + " Elements: " + alg2avg[a]);
		    loopnum = loopnum + 1000;
	    }
	    System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-\n");

	    System.out.println("Quick Sort Run Times!\n");
	    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-\n");
	    loopnum = 1000;
	    for(int a=0;a<alg3avg.length;a++){
		    System.out.println("Run Average For " + loopnum + " Elements: " + alg3avg[a]);
		    loopnum = loopnum + 1000;
	    }
    }


    //Insertion sort, according to the notes. Organizes numbers into increasing order.
    private static void insertSort(int[] a){
        int n = a.length;
        for (int i=1; i<n; ++i) {
            int key = a[i];
            int j = i-1;

            while (j>=0 && a[j] > key)
            {
                a[j+1] = a[j];
                j = j-1;
            }
            a[j+1] = key;
        }
    }

    //Heapsort. Builds max heap, then heapifies all elements. Then runs through the all elements in the array and max heapifies it.
    private static void heapSort(int[] a) {
        int n = a.length;

        buildMaxHeap(a,n);

        for(int i = n - 1; i >= 1; i--) {
            int temp = a[0];
            a[0] = a[i];
            a[i] = temp;

            n = n-1;//modify the heapsize smaller by 1

            maxHeapify(a,i,0);
        }
    }

    //Build max heap, as followed by notes.
    private static void buildMaxHeap(int[] a, int n){
        for(int k=(n/2)-1; k>=0; k--) {
            maxHeapify(a,n,k);
        }
    }

    //Max heapify, as followed by notes, with a change of no else statement.
    private static void maxHeapify(int[] a, int n, int i){
        int l = 2*i + 1;
        int r = l + 1;
        int largest = i; //Stating value of largest here, would save a line of code and save time of comparison. This value only changes on left being largest or right being largest anyways

        if(l<n && a[l]>a[largest])
            largest = l;
        if(r<n && a[r]>a[largest])
            largest = r;
        if(largest != i){
            int temp = a[i];
            a[i] = a[largest];
            a[largest] = temp;
            maxHeapify(a,n,largest);
        }

    }

    //Quick sort function, as followed by notes and called recursively.
    private static void quickSort(int[] a, int p, int r){
        if(p<r){
            int q = partition(a,p,r);
            quickSort(a,p,q-1);
            quickSort(a,q+1,r);
        }
    }

    //Partition function, as followed by notes.
    private static int partition(int[] a, int p, int r){
        int x = a[r];
        int i = p-1;
        int temp = 0;

        for(int j=p;j<=r-1;j++){
            if (a[j] <= x){
                i++;
                temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        temp = a[i+1];
        a[i+1] = a[r];
        a[r] = temp;
        return i+1;
    }
}
