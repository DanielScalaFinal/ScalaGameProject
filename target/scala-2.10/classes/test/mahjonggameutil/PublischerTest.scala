package mahjonggameutil

import org.specs2.mutable._
import mahjonggameutil._
import main.scala.mahjonggameutil.SimpleSubscriber
import main.scala.mahjonggameutil.SimplePublisher
import main.scala.mahjonggameutil.SimpleNotification

class PublischerTest  extends SpecificationWithJUnit {

  case object MyNotification extends SimpleNotification
  
  "A SimpleSubscriber" should {
	    "process notifications" in {
		      val subscriber = new SimpleSubscriber {
		        var notificated = false
		        notificationProcessor = {
		          case _ => notificated = true
		        }
		      }
		      
		      subscriber.notificated must beFalse
		      subscriber.notificationProcessor.apply(MyNotification)
		      subscriber.notificated must beTrue
	    }
  }
  
  "A SimplePublisher" should {    
    
	    "can attach and remove subscribers" in {
		      val publisher = new SimplePublisher {}
		      val subscriber = new SimpleSubscriber {}
		      
		      publisher.addSubscriber(subscriber)
		      publisher.subscribers must have size(1)
		      publisher.remSubscriber(subscriber)
		      publisher.subscribers must have size(0)
	    }
  }
  
  "A SimplePublisher" should {
    
	  	"can notifiy subscribers" in {
	  		  val publisher = new SimplePublisher {}
		      val subscriber = new SimpleSubscriber {
			        var notificated = false
			        notificationProcessor = { case _ => notificated = true}
		      }
		      
		      
		      publisher.addSubscriber(subscriber)
		      publisher.sendNotification(MyNotification)
		      subscriber.notificated must beFalse
		      publisher.remSubscriber(subscriber)
		      subscriber.notificated must beTrue
	    }
   }

}