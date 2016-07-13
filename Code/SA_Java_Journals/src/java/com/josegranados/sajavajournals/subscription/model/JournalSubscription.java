package com.josegranados.sajavajournals.subscription.model;

import com.josegranados.sajavajournals.journal.model.Journal;
import com.josegranados.sajavajournals.user.model.User;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * SA_Java_Journals
 * @author jose - 02.07.2016 
 * @Title: JournalSubscription
 * @Description: description
 *
 * Changes History
 */
@Entity
@Table(name = "JournalSubscription")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "JournalSubscription.findAll", query = "SELECT j FROM JournalSubscription j")})
public class JournalSubscription implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJournalSubscription")
	private Integer idJournalSubscription;
	@JoinColumn(name = "user", referencedColumnName = "idUser")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User user;
	@JoinColumn(name = "journal", referencedColumnName = "idJournal")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Journal journal;
	@Basic(optional = false)
    @NotNull
    @Column(name = "subscription_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date subscriptionDate;

	public JournalSubscription() {
	}

	public JournalSubscription(Integer idJournalSubscription) {
		this.idJournalSubscription = idJournalSubscription;
	}

	public Integer getIdJournalSubscription() {
		return idJournalSubscription;
	}

	public void setIdJournalSubscription(Integer idJournalSubscription) {
		this.idJournalSubscription = idJournalSubscription;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idJournalSubscription != null ? idJournalSubscription.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof JournalSubscription)) {
			return false;
		}
		JournalSubscription other = (JournalSubscription) object;
		if ((this.idJournalSubscription == null && other.idJournalSubscription != null) || (this.idJournalSubscription != null && !this.idJournalSubscription.equals(other.idJournalSubscription))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.josegranados.sajavajournals.book.model.JournalSubscription[ idJournalSubscription=" + idJournalSubscription + " ]";
	}

}