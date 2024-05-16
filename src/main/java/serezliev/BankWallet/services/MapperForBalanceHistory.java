package serezliev.BankWallet.services;

import serezliev.BankWallet.model.BalanceHistoryEntity;
import serezliev.BankWallet.view.BalanceHistoryViewModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MapperForBalanceHistory {


    public static BalanceHistoryViewModel mapToDTO(BalanceHistoryEntity entity) {
        BalanceHistoryViewModel dto = new BalanceHistoryViewModel();
        dto.setId(entity.getId());
        dto.setBalanceAmount(entity.getBalanceAmount());
        dto.setDateAndTime(entity.getDateAndTime().toString()); // примерно форматиране за дата и време
        return dto;
    }

    public static List<BalanceHistoryViewModel> mapToDTOList(Optional<BalanceHistoryEntity> entities) {
        return entities.stream()
                .map(MapperForBalanceHistory::mapToDTO)
                .collect(Collectors.toList());
    }
}
