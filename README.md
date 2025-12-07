# İstatistiksel Analiz Aracı: Tek Yönlü ANOVA ve Welch Testi

Bu Java projesi, kullanıcıdan alınan verilerle gruplar arası ortalama farklarını test eden kapsamlı bir istatistiksel analiz aracıdır. Program, veri setinin varyans homojenliğini otomatik olarak test eder ve sonuca göre en uygun analiz yöntemini (Klasik ANOVA veya Welch ANOVA) uygular.

## 🚀 Projenin Amacı

İstatistiksel analizlerde, gruplar arası karşılaştırma yaparken varyansların eşit olup olmadığı (homojenlik) kullanılacak testin doğruluğu için kritiktir. Bu yazılım:
1.  Kullanıcıdan dinamik olarak grup ve veri girişi alır.
2.  **Levene Testi** mantığıyla varyans homojenliğini kontrol eder.
3.  Eğer varyanslar eşitse **Klasik Tek Yönlü ANOVA** uygular.
4.  Eğer varyanslar eşit değilse **Welch ANOVA** testini devreye sokar.

## 📂 Dosya Yapısı

Proje `istatistikk` paketi altında çalışmaktadır ve temel olarak iki ana bileşenden oluşur (Tahmini yapı):

* **`istatistik.java`**: Ana (Main) sınıf. Kullanıcı etkileşimi, veri girişi ve test akışının kontrolü buradadır.
* **`metotlar.java`**: Hesaplama motoru. Kareler toplamı, serbestlik dereceleri, F istatistiği, Levene testi ve Welch testi hesaplamalarını içeren yardımcı metotları barındırır.

## ⚙️ Özellikler

* **Dinamik Veri Girişi:** İstenilen sayıda grup ve her grup için farklı sayıda veri girilebilir.
* **Hipotez Kurulumu:** H0 ve H1 hipotezlerini otomatik olarak belirler ve ekrana yazar.
* **Otomatik Karar Mekanizması:** Varyans homojenliğine göre algoritmayı değiştirir.
* **Adım Adım Hesaplama:**
    * Genel Kareler Toplamı (GKT)
    * Gruplar Arası Kareler Toplamı (GAKT)
    * Grup İçi Kareler Toplamı (GİKT)
    * Serbestlik Dereceleri (df)
    * F Hesap ve F Kritik Karşılaştırması

## 🛠 Kurulum ve Çalıştırma

Projeyi kendi bilgisayarınızda çalıştırmak için Java Development Kit (JDK) yüklü olmalıdır.

1.  Dosyaları indirin.
2.  Terminal veya Komut İstemi'ni açın.
3.  Derleme işlemi için:
    ```bash
    javac istatistikk/*.java
    ```
4.  Çalıştırmak için:
    ```bash
    java istatistikk.istatistik
    ```
### Önemli Not:
Bu kodda `metotlar.leveneTest`, `metotlar.GKT` gibi çağrılar var. Bu `metotlar` sınıfının (class) kodları senin elinde mevcut, değil mi? GitHub'a yüklerken **hem `istatistik.java` hem de `metotlar.java`** dosyalarını yüklemeyi unutma. Yoksa kod başkasında çalışmaz.
## 📊 Örnek Kullanım Senaryosu

Program çalıştığında aşağıdaki gibi bir akış izler:

```text
alfa değerini giriniz (örnek: 0.05): 0.05
Kaç grup olacak? 3
1. grup için kaç veri girilecek? 4
... (Veri girişleri yapılır) ...

--> Varyans homojenliği sağlandı. Klasik ANOVA yapılıyor.

Hipotezler
H0: Tüm grup ortalamaları eşittir.
H1: En az bir grup ortalaması diğerlerinden farklıdır.

[Program hesaplanan F değerini ve sonuç yorumunu basar]
