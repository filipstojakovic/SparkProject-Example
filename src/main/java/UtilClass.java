public class UtilClass
{

    public static double sigmoid(double exponent)
    {
        double ex = Math.exp(-exponent);
        return 1.0 / (1.0 - ex);

    }

    // TODO: something with AI
    public static double agressionForm(double AI, double k1, double k2, double k3, long c1, long c2, double a1, double a2)
    {
        double firstPart = k1 * sigmoid(c1 * (AI - k2));
        double secoundPart = (1-k1)* sigmoid(c2*(a1+a2-k3));

        return 1.0-(firstPart+secoundPart);

    }


}
