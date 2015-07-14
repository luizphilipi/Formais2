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

    public static void error(String e) {
        JOptionPane.showMessageDialog(null, e);
        System.exit(0);
    }

    public static void main(String[] args) {
        z.sentenca = "id id" + " $";
        String x = z.alex("");
        x = z.E(x);
        if (x.equals("$")) {
            z.error("Sentença reconhecida");
        } else {
            z.error("Sentença não reconhecida");
        }
    }

    public static String E(String x) {
// first de T E1
        if (x.equals("(") || x.equals("id")) {

            x = z.T(x);

            x = z.E1(x);
            return x;
        }
        z.error("esperava-se ( ou id ");
        return x;
    }

    public static String E1(String x) {
        if (x.equals("+")) {
            x = z.alex(x);
            x = z.T(x);

            x = z.E1(x);

            return x;
        } else {
            return x;
        }
    }

    public static String T(String x) {
// first de F T1
        if (x.equals("(") || x.equals("id")) {

            x = z.F(x);

            x = z.T1(x);
            return x;
        }
        z.error("esperava-se ( ou id ");
        return x;
    }

    public static String T1(String x) {
        if (x.equals("*")) {
            x = z.alex(x);
            x = z.F(x);

            x = z.T1(x);

            return x;
        } else {
            return x;
        }
    }

    public static String F(String x) {
        if (x.equals("(")) {
            x = z.alex(x);
            x = z.E(x);
            if (x.equals(")")) {
                x = z.alex(x);
                return x;
            }

        } else if (x.equals("id")) {
            x = z.alex(x);
            return x;
        }
        z.error("esperava-se ) ou ( ou id  ");
        return x;
    }
}
