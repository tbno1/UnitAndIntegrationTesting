package tutorial.domain;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;
import static tutorial.persistence.Database.persist;

@RunWith(JMockit.class)
public final class MyBusinessServiceTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Tested
    final EntityX data = new EntityX(1, "abc", "someone@somewhere.com");

    @Tested(fullyInitialized = true)
    MyBusinessService businessService;

    @Mocked
    SimpleEmail anyEmail;

    @Test
    public void doBusinessOperationXyz() throws Exception {

        EntityX existingItem = new EntityX(1, "AX5", "abc@xpta.net");

        persist(existingItem);

        businessService.doBusinessOperationXyz();

        assertNotEquals(0, data.getId()); // implies "data" was persisted

        new Verifications() {{
            anyEmail.send();
            times = 1;
        }};
    }

    @Test
    public void doBusinessOperationXyzWithInvalidEmailAddress() throws Exception {
        String email = "invalid address";
        data.setCustomerEmail(email);
        new Expectations() {{
            anyEmail.addTo(email);
            result = new EmailException();
        }};
        thrown.expect(EmailException.class);

        businessService.doBusinessOperationXyz();
    }
}