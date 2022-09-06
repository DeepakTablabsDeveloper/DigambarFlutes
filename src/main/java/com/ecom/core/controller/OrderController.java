package com.ecom.core.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.core.domain.JsonObjectFormat;
import com.ecom.core.dto.BaughtProducts;
import com.ecom.core.dto.Cart;
import com.ecom.core.dto.Discount;
import com.ecom.core.dto.Orders;
import com.ecom.core.dto.Products;
import com.ecom.core.dto.Wallet;
import com.ecom.core.exception.CustomParameterizedException;
import com.ecom.core.repository.BaughtPruductsRepository;
import com.ecom.core.repository.CartRepository;
import com.ecom.core.repository.DiscountRepository;
import com.ecom.core.repository.OrderRepository;
import com.ecom.core.repository.ProductConfigurationRepository;
import com.ecom.core.repository.ProductsRepository;
import com.ecom.core.repository.WalletReposiory;
import com.ecom.core.util.FireBaseNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "OrderController", description = "Order details")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class OrderController {

	private final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderRepository OrderRepository;

	@Autowired
	WalletReposiory WalletReposiory;

	@Autowired
	BaughtPruductsRepository baughtProductRepository;

	@Autowired
	CartRepository CartRepository;

	@Autowired
	DiscountRepository DiscountRepository;

	@Autowired
	ProductsRepository ProductsRepository;

	@Autowired
	FireBaseNotifications fireBaseNotifications;

	@Autowired
	ProductConfigurationRepository configurationRepository;

	@PutMapping("/Order")
	public ResponseEntity<String> updateOrder(@RequestBody Orders orders) throws JsonProcessingException {
		log.debug("REST request to update Attendance : {}", orders);
		Orders order = OrderRepository.findById(orders.getId());
		if (orders.getId() == null || order == null) {
			CustomParameterizedException customException = new CustomParameterizedException("Invalid Id", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}

		if (orders.getAddress() != null)
			order.setAddress(orders.getAddress());
		if (orders.getCustomerName() != null)
			order.setCustomerName(orders.getCustomerName());

		if (orders.getStatus() != null)
			order.setStatus(orders.getStatus());
		if (orders.getDeliverPersonMobileNumber() != null)
			order.setDeliverPersonMobileNumber(orders.getDeliverPersonMobileNumber());
		if (orders.getDeliveryPersonName() != null)
			order.setDeliveryPersonName(orders.getDeliveryPersonName());

		try {
			Orders orderDetails = OrderRepository.save(order);
			List<BaughtProducts> list = baughtProductRepository.findByOrderId(orderDetails.getId());
			if (!list.isEmpty()) {
				for (BaughtProducts baughtProducts : list) {
					Products product = ProductsRepository.findById(baughtProducts.getProductId());
					if (product != null && product.getDiscountId() != null && product.getDiscountId() != 0) {
						Discount discount = DiscountRepository.findById(product.getDiscountId());
						Long discountPercent = discount.getPercentageOfBaseCost();
						Long productCost = product.getCost();
						double d1 = (double) discountPercent;
						double price = productCost * (d1 / 100);
						Long lPrice = (long) price;
						product.setCostAfterDiscount(product.getCost() - lPrice);
						product.setDiscount(discount);
					}
					baughtProducts.setProduct(product);
				}
			}
			orderDetails.setBaughtProducts(list);

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Order updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(orderDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Order is not updated successfully");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}

	@GetMapping("/order/orderId")
	public ResponseEntity<String> orderListByOrderId(@RequestParam("orderId") Long orderId)
			throws JsonProcessingException {

		Orders order = OrderRepository.findById(orderId);

		if (order != null) {
			if (order.getProductConfigId() != null && order.getProductConfigId() != 0) {
				order.setConfiguration(configurationRepository.findOne(order.getProductConfigId()));
			}
			List<BaughtProducts> list = baughtProductRepository.findByOrderId(order.getId());
			if (list != null && !list.isEmpty()) {
				for (BaughtProducts baughtProducts : list) {
					Products product = ProductsRepository.findById(baughtProducts.getProductId());
					if (product != null && product.getDiscountId() != null && product.getDiscountId() != 0) {
						Discount discount = DiscountRepository.findById(product.getDiscountId());
						Long discountPercent = discount.getPercentageOfBaseCost();
						Long productCost = product.getCost();
						double d1 = (double) discountPercent;
						double price = productCost * (d1 / 100);
						Long lPrice = (long) price;
						product.setCostAfterDiscount(product.getCost() - lPrice);
						product.setDiscount(discount);
					}
					baughtProducts.setProduct(product);
				}
				order.setBaughtProducts(list);
			}

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Order Details fetched successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(order);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Orders  Details Not Found");
			jsonobjectFormat.setSuccess(false);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@GetMapping("/order/customerId")
	public ResponseEntity<String> orderListByCustomerId(@RequestParam("customerId") Long customerId)
			throws JsonProcessingException {

		List<Orders> orderDetails = OrderRepository.findByCustomerId(customerId);
		if (orderDetails != null && !orderDetails.isEmpty()) {
			for (Orders order : orderDetails) {
				List<BaughtProducts> list = baughtProductRepository.findByOrderId(order.getId());
				if (order.getProductConfigId() != null && order.getProductConfigId() != 0) {
					order.setConfiguration(configurationRepository.findOne(order.getProductConfigId()));
				}
				if (list != null && !list.isEmpty()) {
					for (BaughtProducts baughtProducts : list) {
						Products product = ProductsRepository.findById(baughtProducts.getProductId());
						if (product != null && product.getDiscountId() != null && product.getDiscountId() != 0) {
							Discount discount = DiscountRepository.findById(product.getDiscountId());
							Long discountPercent = discount.getPercentageOfBaseCost();
							Long productCost = product.getCost();
							double d1 = (double) discountPercent;
							double price = productCost * (d1 / 100);
							Long lPrice = (long) price;
							product.setCostAfterDiscount(product.getCost() - lPrice);
							product.setDiscount(discount);
						}
						baughtProducts.setProduct(product);
					}
					order.setBaughtProducts(list);
				}

			}
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Orders for user fetched successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(orderDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("orders not founds");
			jsonobjectFormat.setSuccess(false);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}

	@GetMapping("/order/order")
	public ResponseEntity<String> orderListbyOrder(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) throws JsonProcessingException {

		if (size != null) {
		} else {
			size = 10;
		}
		if (page != null) {
		} else {
			page = 0;
		}
		Pageable pageable = new PageRequest(page, size);

		List<Orders> orderDetails = OrderRepository.findAllorderBydateOrder();

		if (orderDetails != null && !orderDetails.isEmpty()) {
			for (Orders order : orderDetails) {

				List<BaughtProducts> list = baughtProductRepository.findByOrderId(order.getId());

				if (order !=null && order.getProductConfigId() != null && order.getProductConfigId() != 0) {
					order.setConfiguration(configurationRepository.findOne(order.getProductConfigId()));
				}
				if (list != null && !list.isEmpty()) {
					for (BaughtProducts baughtProducts : list) {
						Products product = ProductsRepository.findById(baughtProducts.getProductId());
						if (product != null && product.getDiscountId() != null && product.getDiscountId() != 0) {
							Discount discount = DiscountRepository.findById(product.getDiscountId());
							Long discountPercent = discount.getPercentageOfBaseCost();
							Long productCost = product.getCost();
							double d1 = (double) discountPercent;
							double price = productCost * (d1 / 100);
							Long lPrice = (long) price;
							product.setCostAfterDiscount(product.getCost() - lPrice);
							product.setDiscount(discount);
						}
						baughtProducts.setProduct(product);
					}
				}
				order.setBaughtProducts(list);
			}
			Page<Orders> result = new PageImpl<Orders>(orderDetails, pageable, orderDetails.size());
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Record Fetched successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(result);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage(" record not fetched successfully");
			jsonobjectFormat.setSuccess(false);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}

	@Transactional
	@PostMapping("/order")
	public ResponseEntity<String> placeOrder(@RequestBody Orders requestOrder) throws JsonProcessingException {
		log.debug("REST request to place order", requestOrder);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		String date = dateFormat.format(currentDate);
		requestOrder.setOrderDate(date);
		requestOrder.setDeliveryDate(getDateTwoDaysAfter(date));
		Orders order = OrderRepository.save(requestOrder);
		try {
			List<BaughtProducts> list = requestOrder.getBaughtProducts();
			List<BaughtProducts> outList = new ArrayList<BaughtProducts>();
			Long walletAddAmount = new Long(0);
			for (BaughtProducts baughtProducts : list) {
				baughtProducts.setOrderId(order.getId());
				BaughtProducts temp = baughtProductRepository.save(baughtProducts);
				Cart cart = CartRepository.findByCustomerIdAndProductId(order.getCustomerId(), temp.getProductId());
				if (cart != null)
					CartRepository.deleteByCustomerIdAndProductId(order.getCustomerId(), temp.getProductId());

				Products prod = ProductsRepository.findById(temp.getProductId());
				if (prod.getWalletCreditAmt() != null) {

					walletAddAmount = walletAddAmount + prod.getWalletCreditAmt();
				}

				temp.setProduct(prod);
				outList.add(temp);
			}
			order.setBaughtProducts(outList);
			Wallet wallet = WalletReposiory.findByCustomerId(order.getCustomerId());

			if (wallet != null) {
				if (order.getWalletAmtUsed() != null & wallet.getAmount() != null && order.getWalletAmtUsed() != 0)
					wallet.setAmount(wallet.getAmount().subtract(BigDecimal.valueOf(order.getWalletAmtUsed())));

				if (walletAddAmount != null & walletAddAmount != 0)
					wallet.setAmount(wallet.getAmount().add(BigDecimal.valueOf(walletAddAmount)));

				wallet = WalletReposiory.save(wallet);

			} else if (wallet == null & walletAddAmount != null && walletAddAmount != 0) {
				wallet = new Wallet(order.getCustomerId(), BigDecimal.valueOf(order.getWalletAmtUsed()));
				wallet = WalletReposiory.save(wallet);
			}

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Order placed successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(order);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("unable to place order");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}

	@PutMapping("/orders/statusUpdate")
	public ResponseEntity<String> updateOrderS(@RequestParam Long id, @RequestParam String status)
			throws JsonProcessingException {
		log.debug("REST request to update status : {}", status);

		Orders order = OrderRepository.findById(id);
		if (status != null)
			order.setStatus(status);
		Orders orderDetails = OrderRepository.save(order);

		if (orderDetails != null) {

			List<BaughtProducts> list = baughtProductRepository.findByOrderId(order.getId());
			if (list != null && !list.isEmpty()) {
				for (BaughtProducts baughtProducts : list) {
					Products product = ProductsRepository.findById(baughtProducts.getProductId());
					if (product.getDiscountId() != null) {
						Discount discount = DiscountRepository.findById(product.getDiscountId());
						Long discountPercent = discount.getPercentageOfBaseCost();
						Long productCost = product.getCost();
						double d1 = (double) discountPercent;
						double price = productCost * (d1 / 100);
						Long lPrice = (long) price;
						product.setCostAfterDiscount(product.getCost() - lPrice);
						product.setDiscount(discount);
					}
					baughtProducts.setProduct(product);
				}
			}
			order.setBaughtProducts(list);

			// that means queue already exists
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("status updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(orderDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("status is not updated successfully");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}

	@Transactional
	@DeleteMapping("/Order")
	public ResponseEntity<String> deleteOrder(@RequestParam("id") Long id, HttpServletRequest request)
			throws JsonProcessingException {
		log.debug("REST request to delete Order : {}", id);
		try {
			OrderRepository.deleteById(id);
			baughtProductRepository.deleteByOrderId(id);
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Recorded deleted successfully");
			jsonobjectFormat.setSuccess(true);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Recorded not deleted successfully");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	public static String getDateTwoDaysAfter(String date) {

		System.out.println(date);
		String[] r = date.split("-");
		int year = Integer.parseInt(r[0]);
		int month = Integer.parseInt(r[1]);
		int d = Integer.parseInt(r[2]);

		Calendar calendar = new GregorianCalendar(year, month - 1, d);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String endDate = sdf.format(calendar.getTime());

		// add n days
		calendar.add(Calendar.DATE, 2);

		endDate = sdf.format(calendar.getTime());
		return endDate;

	}

	@GetMapping("/orderById/{id}")
	public Orders getOrderByOrderId(@PathVariable(value = "id") Long id) {
		Orders order = OrderRepository.findById(id);

		order.setOrderTotal(100L);
		return OrderRepository.save(order);

	}

	@GetMapping("/order_Status/customerId")
	public ResponseEntity<String> onlyOrderListByCustomerId(@RequestParam("customerId") Long customerId)
			throws JsonProcessingException {

		List<Orders> orderDetails = OrderRepository.findByCustomerId(customerId);
		if (orderDetails != null && !orderDetails.isEmpty()) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Orders for user fetched successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(orderDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("orders not founds");
			jsonobjectFormat.setSuccess(false);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}

//	@PostMapping("/razorPayOrderVerification")
//	public ResponseEntity<String> razorpayOrderVerification(@RequestParam String razorpayPaymentId,@RequestParam String razopayrOrderId, @RequestParam String razorpaySignature)throws JsonProcessingException
//	{
//		RazorpayClient client =null;
//		try {
//			
//			//Add the key and secret
//			client = new RazorpayClient(razorpayKeyId, razorpayKeySecert);
//
//		JSONObject orderOption = new JSONObject();
//
//			  orderOption.put("razor_paymet_id", razorpayPaymentId);
//			  orderOption.put("razor_order_id", razopayrOrderId);
//			  orderOption.put("razor_signature", razorpaySignature);
//			  boolean signatureBoolean = Utils.verifyPaymentSignature(orderOption,razorpayKeySecert);
//			  
//			  JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			  ObjectMapper Obj = new ObjectMapper(); 
//			  if(signatureBoolean)
//			  {
//				 jsonobjectFormat.setMessage("Payment successful and signature verified.");
//				 jsonobjectFormat.setSuccess(true);
//				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 		return ResponseEntity.ok().body(customExceptionStr);
//			  } else {
//				  jsonobjectFormat.setMessage("Payment unsuccessful and signature not verified");
//				  jsonobjectFormat.setSuccess(false);
//				  String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//			 		return ResponseEntity.ok().body(customExceptionStr);
//			  }
//		
//		} catch (Exception e) {
//
//			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 jsonobjectFormat.setMessage("Payment unsuccessful and signature not verified");
//			 jsonobjectFormat.setSuccess(false);
//	           ObjectMapper Obj = new ObjectMapper(); 
//				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 		return ResponseEntity.ok().body(customExceptionStr);
//		
//		}
//}

//	@ExceptionHandler(NotFoundException.class)
//	public ResponseEntity<?> getCustomeException(NotFoundException exp) throws JsonProcessingException{
//		 CustomeException ex=new CustomeException();
//		 ex.setMessage(ex.getMessage());
//		 ex.setStatus(HttpStatusCodeE);
//	}

}
