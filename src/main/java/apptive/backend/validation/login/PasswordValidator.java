package apptive.backend.validation.login;

import apptive.backend.exception.ExceptionEnum;
import apptive.backend.exception.login.PwdConditionException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final int MIN_SIZE = 8;
    private static final int MAX_SIZE = 20;
    private static final String regexPassword = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{" + MIN_SIZE
            + "," + MAX_SIZE + "}$";

    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValidPassword = password.matches(regexPassword);

        if(!isValidPassword) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            MessageFormat.format("{0}자 이상 {1}자 이하의 숫자, 영문자, 특수문자를 포함한 비밀번호를 입력해주세요.", MIN_SIZE, MAX_SIZE))
                    .addConstraintViolation();

            throw new PwdConditionException(ExceptionEnum.PWD_CONDITION_EXCEPTION);
        }
        return true;
    }

    public boolean isValid(String password) {
        return password.matches(regexPassword);
    }
}
