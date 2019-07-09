package com.spapi;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/friends")
public class FriendRetrieveControllers {

	@Autowired
	private FriendRepository friendRepository;

	@Autowired
	private FriendsConnectionRepository friendsConnectionRepository;

	@GetMapping(path = "/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser(@RequestParam String email) {
		Friend n = new Friend();
		n.setEmail(email);
		friendRepository.save(n);
		return "Saved";
	}

	@DeleteMapping(path = "/remove/{id}")
	public String removeUser(@PathVariable int id) {
		friendRepository.deleteById(id);
		return "Saved";
	}

	@DeleteMapping(path = "/removeAll")
	public String removeAllUser() {
		friendRepository.deleteAll();
		return "Saved";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Friend> getAllUsers() {
		return friendRepository.findAll();
	}

	@GetMapping(path = "/addFriend")
	public @ResponseBody String addNewFriend(@RequestParam String UserEmailA, String UserEmailB) {

		int userEmA = 0;
		int userEmB = 0;
		Boolean isFriend = false;
		Boolean isBlocked = false;
		String statueMsg = "";

		for (Friend friend : friendRepository.findByEmail(UserEmailA)) {
			System.out.println(friend.id.intValue());
			userEmA = friend.id.intValue();
		}

		for (Friend friend : friendRepository.findByEmail(UserEmailB)) {
			System.out.println(friend.id.intValue());
			userEmB = friend.id.intValue();
		}

		isFriend = isItfriendMethod(UserEmailA, UserEmailB);
		isBlocked = isItBlockMethod(UserEmailA, UserEmailB);

		if (isFriend == false) {
			if (isBlocked == false) {
				FriendsConnection addedFriend = new FriendsConnection();
				addedFriend.setConnectedUserA(userEmA);
				addedFriend.setConnectedUserB(userEmB);
				friendsConnectionRepository.save(addedFriend);
				isFriend = true;
				System.out.println("User: " + userEmA + " and User: " + userEmB + " are now friends");
			} else {
				System.out.println("User: " + UserEmailA + " has blocked " + "User: " + UserEmailB);
			}
		} else {
			System.out.println("User: " + UserEmailA + " and " + "User: " + UserEmailB + " are already friend");
		}

		if (isFriend == true) {
			statueMsg = "\"success\": " + isFriend;
		} else {
			statueMsg = "\"success\": " + isFriend;
		}

		return statueMsg;
	}

	@GetMapping(path = "/friendList")
	public @ResponseBody String friendList(@RequestParam String UserEmail) {

		Boolean isSuccess = false;
		String statueMsg = "";

		List<Integer> friendsList = new ArrayList<Integer>();
		friendsList = friendsListMethod(UserEmail);

		List<String> friendsEmailList = new ArrayList<String>();
		for (Friend friend : friendRepository.findAllById(friendsList)) {
			friendsEmailList.add(friend.email);
		}

		if (friendsEmailList.size() > 0) {
			isSuccess = true;
		}

		if (isSuccess == true) {
			statueMsg = "\"success\": " + isSuccess + ", \"friends\": " + friendsEmailList + ", \"count\": "
					+ friendsEmailList.size();
		} else {
			statueMsg = "\"success\": " + isSuccess;
		}

		System.out.println(friendsEmailList);
		return statueMsg;
	}

	@GetMapping(path = "/commonFriendList")
	public @ResponseBody String CommonFriendList(@RequestParam String UserEmailA, String UserEmailB) {

		Boolean isSuccess = false;
		String statueMsg = "";

		List<Integer> friendsListA = new ArrayList<Integer>();
		List<Integer> friendsListB = new ArrayList<Integer>();

		friendsListA = friendsListMethod(UserEmailA);
		friendsListB = friendsListMethod(UserEmailB);

		List<String> friendsEmailListA = new ArrayList<String>();
		List<String> friendsEmailListB = new ArrayList<String>();
		List<String> commonFriendsEmailList = new ArrayList<String>();

		for (Friend friend : friendRepository.findAllById(friendsListA)) {
			friendsEmailListA.add(friend.email);
		}
		System.out.println("friendsEmailListA" + friendsEmailListA);

		for (Friend friend : friendRepository.findAllById(friendsListB)) {
			friendsEmailListB.add(friend.email);
		}
		System.out.println("friendsEmailListB" + friendsEmailListB);

		commonFriendsEmailList = friendsEmailListA;
		commonFriendsEmailList.retainAll(friendsEmailListB);
		System.out.println("commonFriendsEmailList" + commonFriendsEmailList);

		if (commonFriendsEmailList.size() > 0) {
			isSuccess = true;
		}

		if (isSuccess == true) {
			statueMsg = "\"success\": " + isSuccess + ", \"friends\": " + commonFriendsEmailList + ", \"count\": "
					+ commonFriendsEmailList.size();
		} else {
			statueMsg = "\"success\": " + isSuccess;
		}

		return statueMsg;
	}

	@GetMapping(path = "/subscribeFriend")
	public @ResponseBody String subscribeFriend(@RequestParam String SubEmailA, String SubEmailB) {

		int subEmailA = 0;
		int subEmailB = 0;
		Boolean hasSubscribed = false;
		Boolean isSuccess = false;
		String statueMsg = "";

		for (Friend friend : friendRepository.findByEmail(SubEmailA)) {
			System.out.println(friend.id.intValue());
			subEmailA = friend.id.intValue();
		}

		for (Friend friend : friendRepository.findByEmail(SubEmailB)) {
			System.out.println(friend.id.intValue());
			subEmailB = friend.id.intValue();
		}

		for (FriendsConnection subscribeFriend : friendsConnectionRepository.findBySubscribeUserA(subEmailA)) {
			if (subscribeFriend.subscribeUserB.equals(subEmailB)) {
				hasSubscribed = true;
				System.out.println("User: " + SubEmailA + " already subscribeb to User: " + SubEmailB);
			}
		}

		if (hasSubscribed == false) {
			FriendsConnection subscribeFriend = new FriendsConnection();
			subscribeFriend.setSubscribeUserA(subEmailA);
			subscribeFriend.setSubscribeUserB(subEmailB);
			friendsConnectionRepository.save(subscribeFriend);
			isSuccess = true;
		}

		if (isSuccess == true) {
			statueMsg = "\"success\": " + isSuccess;
		} else {
			statueMsg = "\"success\": " + isSuccess;
		}

		return statueMsg;
	}

	@GetMapping(path = "/blockFriend")
	public @ResponseBody String blockFriend(@RequestParam String BlockUserA, String BlockUserB) {

		int blockUserA = 0;
		int blockUserB = 0;
		Boolean isFriend = false;
		Boolean hasBlocked = false;
		Boolean isSuccess = false;
		String statueMsg = "";

		isFriend = isItfriendMethod(BlockUserA, BlockUserB);

		for (Friend friend : friendRepository.findByEmail(BlockUserA)) {
			System.out.println(friend.id.intValue());
			blockUserA = friend.id.intValue();
		}

		for (Friend friend : friendRepository.findByEmail(BlockUserB)) {
			System.out.println(friend.id.intValue());
			blockUserB = friend.id.intValue();
		}

		for (FriendsConnection friendsConnectionA : friendsConnectionRepository.findByConnectedUserA(blockUserA)) {
			if (friendsConnectionA.connectedUserB.equals(blockUserB)) {
				friendsConnectionA.setBlockUserA(blockUserA);
				friendsConnectionA.setBlockUserB(blockUserB);
				friendsConnectionRepository.save(friendsConnectionA);
				System.out.println("Test A " + blockUserA + " and " + blockUserB);
			}
		}

		for (FriendsConnection friendsConnectionB : friendsConnectionRepository.findByConnectedUserB(blockUserA)) {
			if (friendsConnectionB.connectedUserA.equals(blockUserB)) {
				friendsConnectionB.setBlockUserA(blockUserA);
				friendsConnectionB.setBlockUserB(blockUserB);
				friendsConnectionRepository.save(friendsConnectionB);
				System.out.println("Test B " + blockUserA + " and " + blockUserB);
			}
		}

		FriendsConnection friendsConnectionC = new FriendsConnection();
		if (isFriend == false) {
			friendsConnectionC.setBlockUserA(blockUserA);
			friendsConnectionC.setBlockUserB(blockUserB);
			friendsConnectionRepository.save(friendsConnectionC);
			System.out.println("Test C " + blockUserA + " and " + blockUserB);
		}

		for (FriendsConnection friendsConnectionA : friendsConnectionRepository.findBySubscribeUserA(blockUserA)) {
			if (friendsConnectionA.subscribeUserB.equals(blockUserB)) {
				friendsConnectionA.setSubscribeUserA(null);
				friendsConnectionA.setSubscribeUserB(null);
				friendsConnectionRepository.save(friendsConnectionA);
				System.out.println("Test AA " + blockUserA + " and " + blockUserB);
			}
		}

		for (FriendsConnection friendsConnectionB : friendsConnectionRepository.findBySubscribeUserB(blockUserA)) {
			if (friendsConnectionB.subscribeUserA.equals(blockUserB)) {
				friendsConnectionB.setSubscribeUserA(null);
				friendsConnectionB.setSubscribeUserB(null);
				friendsConnectionRepository.save(friendsConnectionB);
				System.out.println("Test BB " + blockUserA + " and " + blockUserB);
			}
		}

		hasBlocked = isItBlockMethod(BlockUserA, BlockUserB);
		if (hasBlocked == true) {
			isSuccess = true;
		}

		if (isSuccess == true) {
			statueMsg = "\"success\": " + isSuccess;
		} else {
			statueMsg = "\"success\": " + isSuccess;
		}

		return statueMsg;
	}

	@GetMapping(path = "/retrieveAllUpdateEmail")
	public @ResponseBody String retrieveAllUpdateEmail(@RequestParam String UserEmail, String textMsg) {

		Boolean isSuccess = false;
		List<String> allEmailList = new ArrayList<String>();
		List<String> emailNonBlkList = new ArrayList<String>();
		List<String> emailFriendList = new ArrayList<String>();
		List<String> subList = new ArrayList<String>();
		String statueMsg = "";

		for (Friend friend : friendRepository.findAll()) {
			if (!friend.getEmail().equals(UserEmail)) {
				allEmailList.add(friend.getEmail());
			}
		}
		System.out.println("Email List :" + allEmailList);

		for (String checkBlock : allEmailList) {
			if (isItBlockMethod(UserEmail, checkBlock) == false) {
				emailNonBlkList.add(checkBlock);
			}
		}
		System.out.println("Email Non-BLock List :" + emailNonBlkList);

		for (String checkFriend : emailNonBlkList) {
			if (isItfriendMethod(UserEmail, checkFriend) == true) {
				emailFriendList.add(checkFriend);
			}
		}
		System.out.println("Email Friend List :" + emailFriendList);

		for (String checkSubscribe : emailNonBlkList) {
			if (isItSubscribedMethod(checkSubscribe, UserEmail) == true) {
				subList.add(checkSubscribe);
			}
		}
		System.out.println("Check Subscribe :" + subList);

		// Combine all list
		Set<String> set = new LinkedHashSet<>(emailFriendList);
		set.addAll(subList);
		List<String> combinedList = new ArrayList<>(set);

		if (combinedList.size() > 0) {
			isSuccess = true;
		}

		System.out.println("Final Email Friend List" + combinedList);

		if (isSuccess == true) {
			statueMsg = "\"success\": " + isSuccess + ", \"recipients\": " + combinedList;
		} else {
			statueMsg = "\"success\": " + isSuccess;
		}

		return statueMsg;
	}

	public Boolean isItfriendMethod(String UserEmailA, String UserEmailB) {

		int userEmA = 0;
		int userEmB = 0;
		Boolean isFriend = false;

		for (Friend friend : friendRepository.findByEmail(UserEmailA)) {
			System.out.println(friend.id.intValue());
			userEmA = friend.id.intValue();
		}

		for (Friend friend : friendRepository.findByEmail(UserEmailB)) {
			System.out.println(friend.id.intValue());
			userEmB = friend.id.intValue();
		}

		for (FriendsConnection friendsConnectionA : friendsConnectionRepository.findByConnectedUserA(userEmA)) {
			if (friendsConnectionA.connectedUserB.equals(userEmB)) {
				isFriend = true;
				System.out.println("Test AAA " + userEmA + " and " + userEmB);
			}
		}

		for (FriendsConnection friendsConnectionB : friendsConnectionRepository.findByConnectedUserB(userEmA)) {
			if (friendsConnectionB.connectedUserA.equals(userEmB)) {
				isFriend = true;
				System.out.println("Test BBB " + userEmA + " and " + userEmB);
			}
		}

		System.out.println("isFriend: " + isFriend);

		return isFriend;
	}

	public Boolean isItBlockMethod(String UserEmailA, String UserEmailB) {

		int userEmA = 0;
		int userEmB = 0;
		Boolean isBlocked = false;

		for (Friend friend : friendRepository.findByEmail(UserEmailA)) {
			System.out.println(friend.id.intValue());
			userEmA = friend.id.intValue();
		}

		for (Friend friend : friendRepository.findByEmail(UserEmailB)) {
			System.out.println(friend.id.intValue());
			userEmB = friend.id.intValue();
		}

		for (FriendsConnection friendsConnectionA : friendsConnectionRepository.findByBlockUserA(userEmA)) {

			if (friendsConnectionA.blockUserB.equals(userEmB)) {
				isBlocked = true;
				System.out.println("Test AAAA " + userEmA + " and " + userEmB);
			}
		}

		for (FriendsConnection friendsConnectionB : friendsConnectionRepository.findByBlockUserB(userEmA)) {
			if (friendsConnectionB.blockUserA.equals(userEmB)) {
				isBlocked = true;
				System.out.println("Test BBBB " + userEmA + " and " + userEmB);
			}
		}

		System.out.println("isBlocked: " + isBlocked);

		return isBlocked;
	}

	public Boolean isItSubscribedMethod(String UserEmailA, String UserEmailB) {

		int userEmA = 0;
		int userEmB = 0;
		Boolean isSubscribed = false;

		for (Friend friend : friendRepository.findByEmail(UserEmailA)) {
			System.out.println(friend.id.intValue());
			userEmA = friend.id.intValue();
		}

		for (Friend friend : friendRepository.findByEmail(UserEmailB)) {
			System.out.println(friend.id.intValue());
			userEmB = friend.id.intValue();
		}

		for (FriendsConnection friendsConnectionA : friendsConnectionRepository.findBySubscribeUserA(userEmA)) {
			if (friendsConnectionA.subscribeUserB.equals(userEmB)) {
				isSubscribed = true;
				System.out.println("Test 111 " + userEmA + " and " + userEmB);
			}
		}

		System.out.println("isFriend: " + isSubscribed);

		return isSubscribed;
	}

	public List<Integer> friendsListMethod(String UserEmail) {

		int userEm = 0;
		List<Integer> friendsList = new ArrayList<Integer>();

		for (Friend friend : friendRepository.findByEmail(UserEmail)) {
			System.out.println(friend.id.intValue());
			userEm = friend.id.intValue();

			for (FriendsConnection friendsConnection : friendsConnectionRepository.findByConnectedUserA(userEm)) {
				System.out.println("ARow: " + friendsConnection.id.intValue());
				System.out.println("AUserA: " + friendsConnection.connectedUserA);
				System.out.println("AUserB: " + friendsConnection.connectedUserB);
				System.out.println();
				friendsList.add(friendsConnection.connectedUserB);
			}

			for (FriendsConnection friendsConnection : friendsConnectionRepository.findByConnectedUserB(userEm)) {
				System.out.println("BRow: " + friendsConnection.id.intValue());
				System.out.println("BUserA: " + friendsConnection.connectedUserA);
				System.out.println("BUserB: " + friendsConnection.connectedUserB);
				System.out.println();
				System.out.println();
				friendsList.add(friendsConnection.connectedUserA);
			}
			System.out.println("friendsList" + friendsList);
		}
		return friendsList;
	}

	public List<Integer> subscribedListMethod(String UserEmail) {

		int userEm = 0;
		List<Integer> subscribedList = new ArrayList<Integer>();

		for (Friend friend : friendRepository.findByEmail(UserEmail)) {
			System.out.println(friend.id.intValue());
			userEm = friend.id.intValue();

			for (FriendsConnection friendsConnectionA : friendsConnectionRepository.findBySubscribeUserA(userEm)) {
				subscribedList.add(friendsConnectionA.subscribeUserB);
			}

			for (FriendsConnection friendsConnection : friendsConnectionRepository.findBySubscribeUserB(userEm)) {
				subscribedList.add(friendsConnection.subscribeUserA);
			}
			System.out.println("subscribedList" + subscribedList);
		}
		return subscribedList;
	}
}
