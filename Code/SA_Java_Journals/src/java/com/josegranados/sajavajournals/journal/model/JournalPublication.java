package com.josegranados.sajavajournals.journal.model;

import com.josegranados.sajavajournals.user.model.Profile;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * SA_Java_Journals
 * @author jose - 02.07.2016 
 * @Title: JournalPublication
 * @Description: description
 *
 * Changes History
 */
@Entity
@Table(name = "JournalPublication")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "JournalPublication.findAll", query = "SELECT j FROM JournalPublication j")})
public class JournalPublication implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJournalPublication")
	private Integer idJournalPublication;
	@Basic(optional = false)
    @NotNull
    @Column(name = "publication_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date publicationDate;
	@Basic(optional = false)
    @Lob
    @Column(name = "content")
	private byte[] content;
	@Size(max = 50)
    @Column(name = "file_name")
	private String fileName;
	@Size(max = 200)
    @Column(name = "description")
	private String description;
	@JoinColumn(name = "journal", referencedColumnName = "idJournal")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Journal journal;
	@JoinColumn(name = "publisher_profile", referencedColumnName = "idProfile")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Profile publisherProfile;

	public JournalPublication() {
	}

	public JournalPublication(Integer idJournalPublication) {
		this.idJournalPublication = idJournalPublication;
	}

	public JournalPublication(Integer idJournalPublication, Date publicationDate, byte[] content) {
		this.idJournalPublication = idJournalPublication;
		this.publicationDate = publicationDate;
		this.content = content;
	}

	public Integer getIdJournalPublication() {
		return idJournalPublication;
	}

	public void setIdJournalPublication(Integer idJournalPublication) {
		this.idJournalPublication = idJournalPublication;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@XmlTransient
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@XmlElement(nillable = true)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement(nillable = true)
	public void setDescription(String description) {
		this.description = description;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Profile getPublisherProfile() {
		return publisherProfile;
	}

	public void setPublisherProfile(Profile publisherProfile) {
		this.publisherProfile = publisherProfile;
	}

	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idJournalPublication != null ? idJournalPublication.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof JournalPublication)) {
			return false;
		}
		JournalPublication other = (JournalPublication) object;
		if ((this.idJournalPublication == null && other.idJournalPublication != null) || (this.idJournalPublication != null && !this.idJournalPublication.equals(other.idJournalPublication))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.josegranados.sajavajournals.book.model.JournalPublication[ idJournalPublication=" + idJournalPublication + " ]";
	}

}