package victor.training.java.advanced.model;

// @Entity - The HOLY ENTITY !
public class Customer {
   private MemberCard memberCard;

   public Customer() {
   }

   public Customer(MemberCard profile) {
      this.memberCard = profile;
   }

   public MemberCard getMemberCard() {
      return memberCard;
   }
}
