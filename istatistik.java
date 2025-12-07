package istatistikk;

import java.util.Scanner;

public class istatistik {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("alfa değerini giriniz (örnek: 0.05): ");
        double alfa = scanner.nextDouble();

        System.out.print("Kaç grup olacak? ");
        int grupSayisi = scanner.nextInt();

        int[][] gruplar = new int[grupSayisi][];
        double[][] gruplarDouble = new double[grupSayisi][];
        int[] grupToplamlari = new int[grupSayisi];
        int[] grupN = new int[grupSayisi];
        int genelToplam = 0;
        double genelKareToplam = 0;
        int toplamVeriSayisi = 0;

        for (int i = 0; i < grupSayisi; i++) {
            System.out.print((i + 1) + ". grup için kaç veri girilecek? ");
            int n = scanner.nextInt();
            gruplar[i] = new int[n];
            gruplarDouble[i] = new double[n];
            grupN[i] = n;

            int grupToplam = 0;
            for (int j = 0; j < n; j++) {
                System.out.print((i + 1) + ". grup " + (j + 1) + ". verisi: ");
                int veri = scanner.nextInt();
                gruplar[i][j] = veri;
                gruplarDouble[i][j] = veri;
                grupToplam += veri;
                genelToplam += veri;
                genelKareToplam += veri * veri;
                toplamVeriSayisi++;
            }
            grupToplamlari[i] = grupToplam;
        }
     boolean varyansHomojen = metotlar.leveneTest(gruplar, alfa);

        if (varyansHomojen) {
            System.out.println("\nVaryans homojenliği sağlandı. Klasik ANOVA yapılıyor.\n");

            System.out.println(" Hipotezler   ");
            System.out.println("H0: Tüm grup ortalamaları eşittir (anlamlı fark yoktur).");
            System.out.println("H1: En az bir grup ortalaması diğerlerinden farklıdır (anlamlı fark vardır).\n");

            metotlar.GKT(genelKareToplam, genelToplam, toplamVeriSayisi);
            metotlar.GAKT(grupToplamlari, grupN, genelToplam, toplamVeriSayisi);
            metotlar.GİKT();
            metotlar.serbestrlikV1(grupSayisi);
            metotlar.serbestlikV2(toplamVeriSayisi, grupSayisi);
            metotlar.karelerortS1();
            metotlar.karelerortS2();
            metotlar.testİstatistigiF();
            metotlar.hesaplaFKritik(alfa);
            metotlar.karsilastir();
        } else {
            System.out.println("\n→ Varyanslar eşit değil. Welch ANOVA başlatılıyor...\n");

            System.out.println(" Hipotezler (Welch ANOVA) ");
            System.out.println("H0: Tüm grup ortalamaları eşittir (anlamlı fark yoktur).");
            System.out.println("H1: En az bir grup ortalaması diğerlerinden farklıdır (anlamlı fark vardır).");

            metotlar.welchAnova(gruplarDouble, alfa);
        }
    }
}
