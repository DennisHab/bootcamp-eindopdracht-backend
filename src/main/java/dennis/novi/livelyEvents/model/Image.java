package dennis.novi.livelyEvents.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    @OneToOne
    private Venue venue;

    private String fileType;

    @Lob
    private byte[] fileData;

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
