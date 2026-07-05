# Οδηγός Ελέγχου (Testing)

Αυτός ο φάκελος (`src/test/`) περιέχει όλο τον κώδικα και τους πόρους που απαιτούνται για τον έλεγχο της λειτουργικότητας, της ορθότητας και της απόδοσης του project.

## Δομή Καταλόγων

Το περιβάλλον ελέγχου είναι οργανωμένο σε δύο βασικούς υποφακέλους:

-   **`java/`**: Περιέχει τον πηγαίο κώδικα Java για τους εκτελεστές των ελέγχων (test runners).
    -   `TestDataStructures.java`: Μια σουίτα ελέγχων για τις βασικές δομές δεδομένων (`List`, `Stack`, `Queue`). Επαληθεύει την ορθότητα, τις οριακές περιπτώσεις (π.χ., λειτουργίες σε κενές δομές) και την απόδοση O(1) για τις βασικές λειτουργίες.
    -   `TestTagChecking.java`: Ένας αυτοματοποιημένος εκτελεστής ελέγχων για τον ελεγκτή ετικετών HTML. Εκτελεί το πρόγραμμα `TagChecking` για κάθε αρχείο `.html` στον φάκελο `resources/tag_checker_test_cases` και συγκρίνει την έξοδό του με τα αναμενόμενα αποτελέσματα στο `solutions.txt`.
    -   `TestNetProfit.java`: Ένας αυτοματοποιημένος εκτελεστής ελέγχων για τον υπολογιστή καθαρού κέρδους. Λειτουργεί παρόμοια, εκτελώντας το πρόγραμμα `NetProfit` για κάθε αρχείο `TC-*.txt` στον φάκελο `resources/net_profit_test_cases` και επικυρώνοντας την έξοδο σε σχέση με το `solutions.txt`.

-   **`resources/`**: Περιέχει τα αρχεία που δεν είναι Java και χρησιμοποιούνται ως είσοδος για τους ελέγχους.
    -   `tag_checker_test_cases/`: Περιέχει διάφορα αρχεία `.html` με σωστές και λανθασμένες δομές ετικετών.
    -   `net_profit_test_cases/`: Περιέχει διάφορα αρχεία `.txt` με διαφορετικές ακολουθίες συναλλαγών μετοχών.

## Πώς να Εκτελέσετε τους Ελέγχους

Όλες οι εντολές πρέπει να εκτελούνται από τον κεντρικό φάκελο του project.

### 1. Μεταγλώττιση του Project

Πρώτα, βεβαιωθείτε ότι τόσο ο κώδικας της κύριας εφαρμογής όσο και ο κώδικας ελέγχου είναι μεταγλωττισμένοι:

```bash
# Μεταγλώττιση του πηγαίου κώδικα της εφαρμογής
javac -d out/production src/main/java/edu/aueb/ds/datastructures/*.java src/main/java/edu/aueb/ds/netprofit/*.java src/main/java/edu/aueb/ds/tagchecking/*.java

# Μεταγλώττιση του πηγαίου κώδικα των ελέγχων
javac -d out/test -cp out/production src/test/java/edu/aueb/ds/*.java src/test/java/edu/aueb/ds/netprofit/*.java src/test/java/edu/aueb/ds/tagchecking/*.java
```

### 2. Εκτέλεση των Σουιτών Ελέγχου

Μπορείτε να εκτελέσετε κάθε σουίτα ελέγχου ξεχωριστά:

-   **Έλεγχος Δομών Δεδομένων:**
    ```bash
    java -cp out/test:out/production edu.aueb.ds.TestDataStructures
    ```

-   **Έλεγχος Ετικετών HTML:**
    ```bash
    java -cp out/test:out/production edu.aueb.ds.tagchecking.TestTagChecking
    ```

-   **Έλεγχος Καθαρού Κέρδους:**
    ```bash
    java -cp out/test:out/production edu.aueb.ds.netprofit.TestNetProfit
    ```

---
