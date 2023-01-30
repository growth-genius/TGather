package com.ysdeveloper.tgather.modules.account.entity;

import com.ysdeveloper.tgather.modules.common.CreatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class Otp extends CreatedEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "otp_id" )
    private Long id;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "account_id" )
    private Account account;

    private String otpNumber;

    private Otp ( Account account, String otpNumber ) {
        this.account = account;
        this.account.getOtpList().add( this );
        this.otpNumber = otpNumber;
    }

    public static Otp of ( Account account, String otpNumber ) {
        return new Otp( account, otpNumber );
    }

}
