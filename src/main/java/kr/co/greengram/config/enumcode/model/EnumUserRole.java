package kr.co.greengram.config.enumcode.model;

import jakarta.persistence.Converter;
import kr.co.greengram.config.enumcode.AbstractEnumCodeConverter;
import kr.co.greengram.config.enumcode.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumUserRole implements EnumMapperType {
    USER_1("01", "유저1"),
    USER_2("02", "유저2"),
    ADMIN("03", "관리자"),
    MENTO("04", "멘토"),
    MANAGER("05", "매니저");

    private final String code;
    private final String value;

    @Converter(autoApply = true)
    public static class CodeConverter extends AbstractEnumCodeConverter<EnumUserRole> {
        public CodeConverter() {
            super(EnumUserRole.class, false);
        }
    }
}
