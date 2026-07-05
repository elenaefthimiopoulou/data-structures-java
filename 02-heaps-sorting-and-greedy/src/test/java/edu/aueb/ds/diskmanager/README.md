# Έλεγχος Υλοποίησης Κλάσης Disk

Σε αυτόν τον φάκελο θα βρείτε τον κώδικα για να ελέγξετε την υλοποίηση της κλάσης `Disk`.

## Εκτέλεση του Test

Για να ελέγξετε την υλοποίησή σας, μπορείτε να εκτελέσετε την `main` μέθοδο του αρχείου `DiskTestMain.java`.

### Οδηγίες Εκτέλεσης από τη Γραμμή Εντολών

1.  Βεβαιωθείτε ότι βρίσκεστε στον κεντρικό φάκελο του project (`DS_exercise_2_student`).
2.  Για την εκτέλεση του test απαιτείται να έχει μεταγλωττιστεί και ο κώδικας των `datastructures`, καθώς η `Disk` τον χρησιμοποιεί. Μεταγλωττίστε τον σχετικό κώδικα με την παρακάτω εντολή:
    ```bash
    javac -d out/production/ src/main/java/edu/aueb/ds/datastructures/*.java src/main/java/edu/aueb/ds/diskmanager/*.java src/test/java/edu/aueb/ds/diskmanager/*.java
    ```
3.  Εκτελέστε τον κώδικα ελέγχου με την παρακάτω εντολή:
    ```bash
    java -cp out/production/ edu.aueb.ds.diskmanager.DiskTestMain
    ```
