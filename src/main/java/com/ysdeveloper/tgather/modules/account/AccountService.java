package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.entity.Account;
import com.ysdeveloper.tgather.modules.account.form.AccountSaveForm;
import com.ysdeveloper.tgather.modules.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public AccountDto saveAccount ( AccountSaveForm accountDto ) {
        accountDto.setPassword( passwordEncoder.encode( accountDto.getPassword() ) );

        accountRepository.findByNickname( accountDto.getNickname() ).ifPresent( account -> {
            throw new BadCredentialsException( "이미 존재하는 닉네임입니다." );
        } );

        Account account = Account.from( accountDto );
        accountRepository.save( account );
        return AccountDto.from( account );
    }

}
