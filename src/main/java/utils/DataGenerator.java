package utils;

import com.github.javafaker.Faker;
import java.util.Locale;

public final class DataGenerator {
    private static final Faker faker = new Faker(Locale.ENGLISH);

    private DataGenerator() {} // Prevent instantiation

    // ================== PERSONAL DATA ================== //
    public static String randomFirstName() {
        return faker.name().firstName();
    }

    public static String randomLastName() {
        return faker.name().lastName();
    }

    public static String randomFullName() {
        return faker.name().fullName();
    }

    public static String randomPrefix() {
        return faker.name().prefix();
    }

    public static String randomSuffix() {
        return faker.name().suffix();
    }

    // ================== CONTACT INFORMATION ================== //
    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static String randomEmail(String domain) {
        return faker.internet().emailAddress().split("@")[0] + "@" + domain;
    }

    public static String randomPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }


    // ================== ADDRESS DATA ================== //
    public static String randomStreetAddress() {
        return faker.address().streetAddress();
    }

    public static String randomCity() {
        return faker.address().city();
    }

    public static String randomState() {
        return faker.address().state();
    }

    public static String randomZipCode() {
        return faker.address().zipCode();
    }

    public static String randomCountry() {
        return faker.address().country();
    }

    // ================== AUTHENTICATION DATA ================== //
    public static String randomPassword() {
        return faker.internet().password(8, 16, true, true, true);
    }

    public static String randomUsername() {
        return faker.name().username();
    }

    // ================== FINANCIAL DATA ================== //
    public static String randomCreditCardNumber() {
        return faker.finance().creditCard();
    }

    public static String randomCreditCardType() {
        return faker.finance().creditCard();
    }

    // ================== DATE/TIME ================== //
    public static String randomFutureDate() {
        return faker.date().future(365, java.util.concurrent.TimeUnit.DAYS).toString();
    }

    public static String randomPastDate() {
        return faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).toString();
    }

    public static String randomBirthdate() {
        return faker.date().birthday().toString();
    }

    // ================== BUSINESS DATA ================== //
    public static String randomCompanyName() {
        return faker.company().name();
    }

    public static String randomJobTitle() {
        return faker.company().profession();
    }

    public static String randomIndustry() {
        return faker.company().industry();
    }

    // ================== INTERNET DATA ================== //
    public static String randomUrl() {
        return faker.internet().url();
    }

    public static String randomUserAgent() {
        return faker.internet().userAgentAny();
    }

    // ================== SPECIALIZED DATA ================== //
    public static String randomProductName() {
        return faker.commerce().productName();
    }

    public static String randomProductCategory() {
        return faker.commerce().department();
    }

    public static String randomPrice() {
        return faker.commerce().price();
    }

    // ================== CUSTOM FORMATTING ================== //
    public static String randomPattern(String pattern) {
        return faker.regexify(pattern);
    }

    public static String randomLorem(int wordCount) {
        return faker.lorem().sentence(wordCount);
    }
}