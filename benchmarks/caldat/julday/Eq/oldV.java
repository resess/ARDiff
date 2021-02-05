package demo.benchmarks.caldat.julday.Eq;
public class oldV {
    public static double snippet(double mmj,  double idj,  double iyyyj) {
        double IGREG=15.0+31.0*(10.0+12.0*1582.0);
        double ja =1.0;
        double jul=0.0;
        double jy=iyyyj;
        double jm=0.0;
        if (jy == 0.0) 
           return 0.0;
        if (jy < 0.0)
            ++jy;
        if (mmj > 2.0) {
            jm=mmj+1.0;
        }
        else {
            --jy;
            jm=mmj+13.0;
        }
        jul = Math.abs(365.0*jy)+Math.sqrt(30.0*jm)+idj+1720995.0;
        if (idj+31.0*(mmj+12.0*iyyyj) <= IGREG ) {
            ja=(0.01*jy);
            jul += 2.0-ja+(0.25*ja);
        }
        return jul;
    }
}