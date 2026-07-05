# Έλεγχος Υλοποίησης Ουράς Προτεραιότητας

Σε αυτόν τον φάκελο θα βρείτε τον κώδικα για να ελέγξετε την υλοποίηση της ουράς προτεραιότητας (`MaxPQ`).

## Εκτέλεση του Test

Για να ελέγξετε την υλοποίησή σας, μπορείτε να εκτελέσετε την `main` μέθοδο του αρχείου `PriorityQueueMain.java`.

### Οδηγίες Εκτέλεσης από τη Γραμμή Εντολών

1.  Βεβαιωθείτε ότι βρίσκεστε στον κεντρικό φάκελο του project (`DS_exercise_2_student`).
2.  Μεταγλωττίστε τον κώδικα του project εκτελώντας την παρακάτω εντολή. Η εντολή αυτή θα μεταγλωττίσει τόσο τον κώδικα της εφαρμογής όσο και τον κώδικα των tests και θα τοποθετήσει τα αρχεία `.class` στον φάκελο `out/`.
    ```bash
    javac -d out/production/ src/main/java/edu/aueb/ds/datastructures/*.java src/test/java/edu/aueb/ds/datastructures/*.java
    ```
3.  Εκτελέστε τον κώδικα ελέγχου με την παρακάτω εντολή:
    ```bash
    java -cp out/production/ edu.aueb.ds.datastructures.PriorityQueueMain
    ```