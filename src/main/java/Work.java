import java.util.Date;

public class Work {

    private final Facility facility;
    private final Date startDate;
    private final Date endDate;
    private final Long financing;
    private final String workType;

    public Work(Facility facility, Date startDate, Date endDate, Long financing, String workType){
        this.facility = facility;
        this.startDate = startDate;
        this.endDate = endDate;
        this.financing = financing;
        this.workType = workType;
    }


    public String getWorkType() {
        return workType;
    }

    public Long getFinancing() {
        return financing;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Facility getFacility() {
        return facility;
    }
}
