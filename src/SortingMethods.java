public class SortingMethods {

    public SortingMethods() {
    }

    public static void sort(Record[] records) {
        SortingMethods.selectionSort(records);
        //SortingMethods.insertionSort(records);
        //SortingMethods.mergeSort(records);
    }

    private static void selectionSort(Record[] records) {
        for (int i = 0; i < records.length - 1; i++) {
            int minIndex = i;
            for (int next = i + 1; next < records.length; next++) {
                if (records[next].getSugars() < records[minIndex].getSugars()) {
                    minIndex = next;
                }
            }

            Record temp = records[minIndex];
            records[minIndex] = records[i];
            records[i] = temp;
        }
    }

    private static void insertionSort(Record[] records) {
        for (int i = 1; i < records.length; i++) {
            Record currNum = records[i];

            int currIndex = i - 1;
            while (currIndex > -1 && records[currIndex].getSugars() > currNum.getSugars()) {
                records[currIndex + 1] = records[currIndex];
                currIndex--;
            }
            records[currIndex + 1] = currNum;
        }
    }
    
    private static void mergeSort(Record[] records) {
        if (records.length > 1) {
            int firstHalf = records.length / 2;
            int secondHalf = records.length - firstHalf;
            Record[] listOne = new Record[firstHalf];
            Record[] listTwo = new Record[secondHalf];

            System.arraycopy(records, 0, listOne, 0, firstHalf);
            System.arraycopy(records, firstHalf, listTwo, 0, secondHalf);

            mergeSort(listOne);
            mergeSort(listTwo);

            merge(listOne, listTwo, records);
        }
    }

    private static void merge(Record[] listOne, Record[] listTwo, Record[] finalList) {
        int indexOne = 0;
        int indexTwo = 0;
        int resultPos = 0;

        while (indexOne < listOne.length && indexTwo < listTwo.length) {
            if (listOne[indexOne].getSugars() < listTwo[indexTwo].getSugars()) {
                finalList[resultPos] = listOne[indexOne];
                indexOne++;
            } else {
                finalList[resultPos] = listTwo[indexTwo];
                indexTwo++;
            }
            resultPos++;
        }

        System.arraycopy(listOne, indexOne, finalList, resultPos, listOne.length - indexOne);
        System.arraycopy(listTwo, indexTwo, finalList, resultPos, listTwo.length - indexTwo);
    }
}
