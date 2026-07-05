# Έλεγχος Ετικετών HTML: Σενάρια Δοκιμών για Οριακές Περιπτώσεις

## 1. Εισαγωγή

Αυτό το έγγραφο περιγράφει μια ολοκληρωμένη σουίτα οριακών περιπτώσεων για τον έλεγχο του προγράμματος ελέγχου ετικετών HTML. Ο στόχος είναι να διασφαλιστεί ότι ο ελεγκτής είναι ακριβής και χειρίζεται απροσδόκητες ή πολύπλοκες εισόδους όπως πρέπει. Κάθε περίπτωση δοκιμής περιλαμβάνει μια περιγραφή, την είσοδο HTML και την αναμενόμενη έξοδο.

---

## 2. Βασικές Περιπτώσεις

Αυτές οι δοκιμές επιβεβαιώνουν τη βασική λειτουργικότητα του προγράμματος με απλές, έγκυρες και μη έγκυρες εισόδους.

| Test Case ID | Description | HTML Input | Expected Output |
| :--- | :--- | :--- | :--- |
| **BC-01** | Απλή Ισορροπημένη | `<html><body><p>Hello</p></body></html>` | `Success: The HTML file has balanced tags.` |
| **BC-02** | Απλή Μη Ισορροπημένη | `<html><body><p>Hello</body></html>` | `Error: Mismatched tag. Expected </p>, but found </body>.` |
| **BC-03** | Απλή Μη Κλεισμένη | `<html><body><p>Hello` | `Error: Reached end of file, but the following tags were not closed: html, body, p` |
| **BC-04** | Επιπλέον Ετικέτα Κλεισίματος | `<html><body><p>Hello</p></body></html></p>` | `Error: Found closing tag </p> with no matching opening tag.` |

---

## 3. Σενάρια Φωλιάσματος

Αυτές οι δοκιμές εστιάζουν στην ικανότητα του προγράμματος να χειρίζεται σωστά και λανθασμένα φωλιασμένες ετικέτες.

| Test Case ID | Description | HTML Input | Expected Output |
| :--- | :--- | :--- | :--- |
| **NS-01** | Βαθύ Σωστό Φώλιασμα | `<html><body><div><p><b><i>Text</i></b></p></div></body></html>` | `Success: The HTML file has balanced tags.` |
| **NS-02** | Λανθασμένο Φώλιασμα | `<html><body><b><p>Mismatched</b></p></body></html>` | `Error: Mismatched tag. Expected </b>, but found </p>.` |
| **NS-03** | Διαπλεκόμενο Φώλιασμα | `<a><b></a></b>` | `Error: Mismatched tag. Expected </b>, but found </a>.` |

---

## 4. Μορφοποίηση και Κενά Διάστημα

Αυτές οι δοκιμές διασφαλίζουν ότι οι παραλλαγές στη μορφοποίηση δεν επηρεάζουν τη λογική ελέγχου των ετικετών.

| Test Case ID | Description | HTML Input | Expected Output |
| :--- | :--- | :--- | :--- |
| **FW-01** | Ετικέτες στην Ίδια Γραμμή | `<html><body><p><b>Bold</b></p></body></html>` | `Success: The HTML file has balanced tags.` |
| **FW-02** | Ετικέτες χωρίς Περιεχόμενο | `<p></p>` | `Success: The HTML file has balanced tags.` |
| **FW-03** | Ετικέτες με Αλλαγές Γραμμής | `<p>\nHello\n</p>` | `Success: The HTML file has balanced tags.` |
| **FW-04** | Χωρίς Κενά Διάστημα | `<html><head></head><body></body></html>` | `Success: The HTML file has balanced tags.` |
| **FW-05** | Ετικέτα με κενά στην αρχή/τέλος | ` <p> text </p> ` | `Success: The HTML file has balanced tags.` |

---

## 5. Αυτο-κλειόμενες Ετικέτες

Αυτές οι δοκιμές επαληθεύουν τον σωστό χειρισμό των αυτο-κλειόμενων ετικετών όπως `<br>` και `<img>`.

| Test Case ID | Description | HTML Input | Expected Output |
| :--- | :--- | :--- | :--- |
| **SC-01** | Απλή Αυτο-κλειόμενη | `<html><body><p>Line 1<br>Line 2</p></body></html>` | `Success: The HTML file has balanced tags.` |
| **SC-02** | Πολλαπλές Αυτο-κλειόμενες | `<p>One<br>Two<br>Three</p>` | `Success: The HTML file has balanced tags.` |
| **SC-03** | Αυτο-κλειόμενη με άλλες ετικέτες | `<div><img src="..."><p>Text</p></div>` | `Success: The HTML file has balanced tags.` |

---

## 6. Λανθασμένες και Κενές Είσοδοι

Αυτές οι δοκιμές ελέγχουν την ανθεκτικότητα του προγράμματος σε κενά αρχεία, περιεχόμενο που δεν είναι HTML και λανθασμένες ετικέτες.

| Test Case ID | Description | HTML Input | Expected Output |
| :--- | :--- | :--- | :--- |
| **ME-01** | Κενό Αρχείο | (File is empty) | `Success: The HTML file has balanced tags.` |
| **ME-02** | Αρχείο χωρίς Ετικέτες | `This is a plain text file.` | `Success: The HTML file has balanced tags.` |
| **ME-03** | Μη Ολοκληρωμένη Ετικέτα | `<html><body><p` | `Error: Reached end of file, but the following tags were not closed: html, body` |
| **ME-04** | Λανθασμένη Ετικέτα | `<html><body><p>Hello</p$!></body></html>` | `Success: The HTML file has balanced tags.` |
| **ME-05** | Κείμενο μεταξύ Ετικετών | `<html> > Text < <body>` | `Success: The HTML file has balanced tags.` |

---

## 7. Ευαισθησία Πεζών-Κεφαλαίων

Αυτές οι δοκιμές διασφαλίζουν ότι η αντιστοίχιση των ετικετών δεν είναι ευαίσθητη στα πεζά-κεφαλαία, σύμφωνα με τα πρότυπα της HTML.

| Test Case ID | Description | HTML Input | Expected Output |
| :--- | :--- | :--- | :--- |
| **CS-01** | Μικτή Γραφή | `<HTML><BODY><P>Hello</p></BODY></html>` | `Success: The HTML file has balanced tags.` |
| **CS-02** | Όλα Κεφαλαία | `<HTML><BODY><P>HELLO</P></BODY></HTML>` | `Success: The HTML file has balanced tags.` |
