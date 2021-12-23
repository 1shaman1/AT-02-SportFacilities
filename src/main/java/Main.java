public class Main {
    public static void main(String[] args) {
        var facilitiesData = new FacilitiesData();
        facilitiesData.readCSV("SportFacilities.csv");
        try {
            var drawer = new Drawer();
            var dbWorker = DBWorker.getInstance();
            dbWorker.removeAllData();
            dbWorker.setData(facilitiesData.getCities(), facilitiesData.getFacilities(), facilitiesData.getWorks());
            System.out.println("C������ ������� ����� ����� �������������� �� 2012 ���: " + dbWorker.getMidFinanceFromYear());
            System.out.println("________________________________________________________________________________________________________________");
            System.out.println(
                    "��������� � ������������ ������� �������������� ����� ������������������� ���������� ���������� � ���������\n" +
                            dbWorker.getMaxFinancingFacility());
            drawer.createDemoPanel(dbWorker.getFinanceByDate());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
