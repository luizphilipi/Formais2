package formais2;


import javax.swing.JOptionPane;

public class z {

    public static String sentenca;
    public static String err = "";

    public static String alex(String a) {
        if (a.length() != 0) {
            sentenca = sentenca.substring(sentenca.indexOf(a + " ") + a.length() + 1);
        }
        return sentenca.split(" ")[0];
    }

    public static void main(String[] args) {
        z.sentenca = "id id" + " $";
        String x = z.alex("");
        x = z.E(x);
        if (err.charAt(err.length() - 2) == 'u') {
            err = err.substring(0, err.length() - 3);
        }
        if (x.equals("$")) {
            JOptionPane.showMessageDialog(null, "reconhecida");
        } else {
            JOptionPane.showMessageDialog(null, "esperava-se" + err);
        }
    }

    public static String E(String x) {
// first de T E1
        if (x.equals("(") || x.equals("id")) {

            x = z.T(x);

            x = z.E1(x);
            return x;
        }
        err += " ( ou id";
        return x;
    }

    public static String E1(String x) {
        if (x.equals("+")) {
            x = z.alex(x);
            x = z.T(x);

            x = z.E1(x);

            return x;
        } else {
            err += " + ou ";
        }
        return x;
    }

    public static String T(String x) {
// first de F T1
        if (x.equals("(") || x.equals("id")) {

            x = z.F(x);

            x = z.T1(x);
            return x;
        }
        err += " ( ou id";
        return x;
    }

    public static String T1(String x) {
        if (x.equals("*")) {
            x = z.alex(x);
            x = z.F(x);

            x = z.T1(x);

            return x;
        } else {
            err += " * ou ";
        }
        return x;
    }

    public static String F(String x) {
        if (x.equals("(")) {
            x = z.alex(x);
            x = z.E(x);
            if (x.equals(")")) {
                x = z.alex(x);
                return x;
            }

            return x;
        } else if (x.equals("id")) {
            x = z.alex(x);
            return x;
        }
        err += " ) ou ( ou id ou ";
        return x;
    }
}
