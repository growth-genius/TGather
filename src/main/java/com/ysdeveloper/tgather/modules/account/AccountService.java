package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.infra.util.GenerateCodeUtil;
import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.entity.Account;
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

    public void saveAccount ( AccountDto accountDto ) {
        accountRepository.save( Account.createAccount( accountDto ) );
    }

    public void authAccount ( AccountDto accountDto ) {
        Account account = accountRepository.findByUserName( accountDto.getUserName() ).orElseThrow( () -> new RuntimeException(
            "해당하는 아이디가 없습니다. 다시 입력해주세요." ) );

        if ( passwordEncoder.matches( accountDto.getPassword(), account.getPassword() ) ) {
            renewOtp( account );
        } else {
            throw new BadCredentialsException( "Bad Credentials." );
        }
    }

    private void renewOtp ( Account account ) {
        String code = GenerateCodeUtil.generateCode();
    }
}
