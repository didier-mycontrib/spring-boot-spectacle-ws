package org.mycontrib.spectacle.service;

import java.util.Collection;
import java.util.List;

import org.mycontrib.spectacle.dao.CustomerDao;
import org.mycontrib.spectacle.dao.PaymentDao;
import org.mycontrib.spectacle.dao.PersonDao;
import org.mycontrib.spectacle.dao.ReservationDao;
import org.mycontrib.spectacle.dao.SessionDao;
import org.mycontrib.spectacle.entity.Payment;
import org.mycontrib.spectacle.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private SessionDao sessionDao;
	
	private static void loadLazyEntity(Object obj){
		if(obj!=null) obj.toString();
	}
	
	private static void loadLazyCollection(Collection<?> c){
		if(c!=null) c.size();
	}

	@Override
	public Reservation findReservationById(Long reservationId) {
		Reservation r =  reservationDao.findById(reservationId).get();
		loadLazyEntity(r.getSession());
		loadLazyEntity(r.getSession().getSpectacle());
		loadLazyEntity(r.getPayment());
		loadLazyEntity(r.getCustomer());
		loadLazyCollection(r.getParticipants());
		return r;
	}

	@Override
	public List<Reservation> findReservationsByCriteria(Long customerId, Long spectacleId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Double computeGlobalAmountFromParticipantsAndSession(Reservation r) {
		Double globalAmount=null;
		Double unitPrice = r.getSession().getSpectacle().getPrice();
		Integer nbParticipants = r.getParticipants().size();
		globalAmount = nbParticipants * unitPrice;
		r.setGlobalAmount(globalAmount);
		return globalAmount;
	}

	@Override
	public Reservation makeReservation(Long customerId, Long sessionId, List<Long> participantIds) {
		Reservation newResa = new Reservation(); //with current date
		newResa.setCustomer(customerDao.findById(customerId).get());
		newResa.setSession(sessionDao.findById(sessionId).get());
		for(Long pId: participantIds) {
			newResa.addParticipant(personDao.findById(pId).get());
		}
		computeGlobalAmountFromParticipantsAndSession(newResa);
		reservationDao.save(newResa);
		return newResa;
	}

	@Override
	public void cancelReservation(Long reservationId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attachPayment(Long reservationId, Payment payment) {
		Reservation r =  reservationDao.findById(reservationId).get();
		Payment persitentPayement  = paymentDao.save(payment);
		r.setPayment(persitentPayement);
        //automatic update/merge in persistent state
	}

}
