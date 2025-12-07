package istatistikk;

import org.apache.commons.math3.distribution.FDistribution;

import java.util.Arrays;

public class metotlar {
    static double GKT, GAKT, GİKT, S1, S2, F, FKritik;
    static int v1, v2;

    public static void GKT(double genKare, int genTop, int N) {
        GKT = genKare - genTop * genTop / (double) N;
        System.out.println("Genel Kareler Toplamı (GKT): " + GKT);
    }

    public static void GAKT(int[] grupToplamlari, int[] grupN, int genelToplam, int toplamVeriSayisi) {
        GAKT = 0;
        for (int i = 0; i < grupToplamlari.length; i++) {
            GAKT += Math.pow(grupToplamlari[i], 2) / (double) grupN[i];
        }
        GAKT -= Math.pow(genelToplam, 2) / (double) toplamVeriSayisi;
        System.out.println("Gruplar Arası Kareler Toplamı (GAKT): " + GAKT);
    }

    public static void GİKT() {
        GİKT = GKT - GAKT;
        System.out.println("Gruplar İçi Kareler Toplamı (GİKT): " + GİKT);
    }

    public static void serbestrlikV1(int grupSayisi) {
        v1 = grupSayisi - 1;
        System.out.println("Serbestlik Derecesi v1: " + v1);
    }

    public static void serbestlikV2(int toplamVeri, int grupSayisi) {
        v2 = toplamVeri - grupSayisi;
        System.out.println("Serbestlik Derecesi v2: " + v2);
    }
 
    public static void karelerortS1() {
        S1 = GAKT / v1;
        System.out.println("S1 (Gruplar Arası Kareler Ortalaması): " + S1);
    }

    public static void karelerortS2() {
        S2 = GİKT / v2;
        System.out.println("S2 (Gruplar İçi Kareler Ortalaması): " + S2);
    }

    public static void testİstatistigiF() {
        F = S1 / S2;
        System.out.println("F (Hesaplanan): " + F);
    }

    public static void hesaplaFKritik(double alfa) {
        FDistribution fd = new FDistribution(v1, v2);
        FKritik = fd.inverseCumulativeProbability(1 - alfa);
        System.out.println("F Kritik Değeri: " + FKritik);
    }

    public static void karsilastir() {
        System.out.println("\n*** Karar ***");
        if (F > FKritik) {
            System.out.println("H0 REDDEDİLİR → Gruplar arasında anlamlı fark vardır.");
        } else {
            System.out.println("H0 KABUL → Gruplar arasında anlamlı fark bulunamamıştır.");
        }
    }

    public static boolean leveneTest(int[][] gruplar, double alfa) {
        int g = gruplar.length;
        int toplamN = 0;
        double[] ortalamalar = new double[g];

        for (int i = 0; i < g; i++) {
            ortalamalar[i] = Arrays.stream(gruplar[i]).average().orElse(0);
            toplamN += gruplar[i].length;
        }

        double[][] mutlakFarklar = new double[g][];
        for (int i = 0; i < g; i++) {
            mutlakFarklar[i] = new double[gruplar[i].length];
            for (int j = 0; j < gruplar[i].length; j++) {
                mutlakFarklar[i][j] = Math.abs(gruplar[i][j] - ortalamalar[i]);
            }
        }

        double genelToplam = 0;
        double genelKareToplamı = 0;
        double[] grupToplamlari = new double[g];
        int[] grupN = new int[g];

        for (int i = 0; i < g; i++) {
            grupN[i] = mutlakFarklar[i].length;
            for (double val : mutlakFarklar[i]) {
                grupToplamlari[i] += val;
                genelToplam += val;
                genelKareToplamı += val * val;
            }
        }

        double GKT = genelKareToplamı - (genelToplam * genelToplam) / toplamN;
        double GAKT = 0;
        for (int i = 0; i < g; i++) {
            GAKT += (grupToplamlari[i] * grupToplamlari[i]) / grupN[i];
        }
        GAKT -= (genelToplam * genelToplam) / toplamN;
        double GIKT = GKT - GAKT;

        int v1 = g - 1;
        int v2 = toplamN - g;
        double S1 = GAKT / v1;
        double S2 = GIKT / v2;
        double F = S1 / S2;

        FDistribution fd = new FDistribution(v1, v2);
        double FKritik = fd.inverseCumulativeProbability(1 - alfa);

        System.out.printf("\nLevene Testi → F = %.4f | F-kritik = %.4f\n", F, FKritik);
        return F <= FKritik;
    }

    
    public static void welchAnova(double[][] gruplar, double alfa) {
        int g = gruplar.length;
        double[] ortalamalar = new double[g];
        double[] varyanslar = new double[g];
        int[] n = new int[g];

        for (int i = 0; i < g; i++) {
            n[i] = gruplar[i].length;
            double toplam = 0;
            for (double v : gruplar[i]) toplam += v;
            ortalamalar[i] = toplam / n[i];

            double ss = 0;
            for (double v : gruplar[i]) ss += Math.pow(v - ortalamalar[i], 2);
            varyanslar[i] = ss / (n[i] - 1);
        }

        double genelOrtalamaPay = 0, genelOrtalamaPayda = 0;
        for (int i = 0; i < g; i++) {
            genelOrtalamaPay += n[i] * ortalamalar[i] / varyanslar[i];
            genelOrtalamaPayda += n[i] / varyanslar[i];
        }
        double genelOrtalama = genelOrtalamaPay / genelOrtalamaPayda;

        double numerator = 0;
        for (int i = 0; i < g; i++) {
            numerator += (n[i] * Math.pow(ortalamalar[i] - genelOrtalama, 2)) / varyanslar[i];
        }
        numerator /= (g - 1);

        double denominator = 0;
        for (int i = 0; i < g; i++) {
            denominator += n[i] / varyanslar[i];
        }
        denominator = (g * (g - 1)) / denominator;

        double F = numerator / (denominator / (g - 1));
        double df1 = g - 1;
        double df2 = denominator;

        FDistribution fd = new FDistribution(df1, df2);
        double FKritik = fd.inverseCumulativeProbability(1 - alfa);

        System.out.printf("Welch ANOVA → F = %.4f | F-kritik = %.4f\n", F, FKritik);
        if (F > FKritik) {
            System.out.println("Sonuç: Gruplar arasında anlamlı fark vardır (H0 reddedildi).");
        } else {
            System.out.println("Sonuç: Gruplar arasında anlamlı fark yoktur (H0 kabul edildi).");
        }
    }
}
