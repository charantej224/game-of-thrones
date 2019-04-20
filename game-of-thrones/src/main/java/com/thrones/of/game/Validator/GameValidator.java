package com.thrones.of.game.Validator;

import com.thrones.of.game.domain.Member;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.domain.Weapon;

import java.util.Optional;

public class GameValidator {

    private Session session = Session.getInstance();

    public boolean canUserFightWithWeapon(Member member, Weapon weapon) {
        return member.getStrength() > weapon.getStrength();
    }

    public boolean isSelectedUserHavingWeapons() {
        Optional<Weapon> optionalWeapon = session.getSelectedWeapons().stream().filter(weapon -> session.getSelected().getStrength() > weapon.getStrength())
                .findFirst();
        return optionalWeapon.isPresent();
    }

    public boolean isEnemyUserHavingWeapons() {
        Optional<Weapon> optionalWeapon = session.getEnemyWeapons().stream().filter(weapon -> session.getEnemy().getStrength() > weapon.getStrength())
                .findFirst();
        return optionalWeapon.isPresent();
    }

}
