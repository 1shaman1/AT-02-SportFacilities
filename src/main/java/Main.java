public class Main {
    public static void main(String[] args) {
        var facilitiesData = new FacilitiesData();
        facilitiesData.readCSV("SportFacilities.csv");
        try {
            var drawer = new Drawer();
            var dbWorker = DBWorker.getInstance();
            dbWorker.removeAllData();
            dbWorker.setData(facilitiesData.getCities(), facilitiesData.getFacilities(), facilitiesData.getWorks());
            System.out.println("Cредний средний общий объем финансирования за 2012 год: " + dbWorker.getMidFinanceFromYear());
            System.out.println("________________________________________________________________________________________________________________");
            System.out.println(
                    "Постройка с максимальным объемом финансирования среди многофункциональных спортивных комплексов и стадионов\n" +
                            dbWorker.getMaxFinancingFacility());
            drawer.createDemoPanel(dbWorker.getFinanceByDate());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
