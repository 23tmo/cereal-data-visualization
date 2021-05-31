public class SearchingMethods {

    public SearchingMethods() {
    }

    public static int search(Record[] records, double rating) {
        return SearchingMethods.linearSearch(records, rating);
        //return SearchingMethods.binarySearch(records, rating);
    }

    private static int linearSearch(Record[] records, double rating) {
        double smallestDif = Math.abs(rating - records[0].getRating()); // random index of records
        int closest = -1;
        for (int i = 0; i < records.length; i++) {
            double curr = records[i].getRating();
            double dif = Math.abs(curr - rating);
            if (dif == 0.0 || dif <= smallestDif) {
                closest = i;
                smallestDif = dif;
            }
        }
        return closest;
    }

    //binary search was referenced from GeeksForGeeks
    private static int binarySearch(Record[] records, double rating) {
        for (int x = 0; x < 20; x++) { // some reason it doesn't work just one time around, so 20 was experimentally found
            for (int i = 0; i < records.length - 1; i++) {
                int minIndex = i;
                for (int next = i + 1; next < records.length; next++) { // finding the index with the minimum amt of sugars
                    if (records[next].getRating() < records[i].getRating()) {
                        minIndex = next;
                    }
                }
                Record temp = records[minIndex]; // the current cereal
                records[minIndex] = records[i]; // making min cereal equal to the current cereal
                records[i] = temp; // making the current cereal equal to the min cereal
            }
        }
        int n = records.length;

        // Corner cases
        if (rating <= records[0].getRating())
            return 0;
        if (rating >= records[n - 1].getRating())
            return n - 1;

        // Doing binary search
        int i = 0, j = n, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;

            if (records[mid].getRating() == rating) {
                return mid;
            }

    // If target is less than array element, then search in left
            if (rating < records[mid].getRating()) {

                // If target is greater than previous
                // to mid, return closest of two
                if (mid > 0 && rating > records[mid - 1].getRating())
                    if ((getClosest(records[mid - 1].getRating(), records[mid].getRating(), rating)) == records[mid - 1].getRating()) {
                        return mid - 1;
                    }
                    else {
                        return mid;
                    }
                // Repeat for left half
                j = mid;
            }
            // If target is greater than mid
            else {
                if (mid < n - 1 && rating < records[mid + 1].getRating()) {
                    if ((getClosest(records[mid].getRating(), records[mid + 1].getRating(), rating)) == records[mid].getRating()) {
                        return mid;
                    } else {
                        return mid + 1;
                    }
                }
                i = mid + 1; // update i
            }
        }
        // Only single element left after search
        return mid;
    }

    public static double getClosest(double val1, double val2, double target) {
        if(target - val1 >= val2 - target) {
            return val2;
        }
        else {
            return val1;
        }
    }
}
