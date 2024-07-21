import com.transactionTrack.ws.model.Transaction;
import com.transactionTrack.ws.model.User;
import com.transactionTrack.ws.repository.TransactionRepository;
import com.transactionTrack.ws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    // Günlük masrafları hesaplayan iş
    @Scheduled(cron = "0 0 0 * * ?") // Her gün gece yarısı çalışır
    public void aggregateDailyExpenses() {
        aggregateExpenses(ChronoUnit.DAYS);
    }

    // Haftalık masrafları hesaplayan iş
    @Scheduled(cron = "0 0 0 * * MON") // Her pazartesi gece yarısı çalışır
    public void aggregateWeeklyExpenses() {
        aggregateExpenses(ChronoUnit.WEEKS);
    }

    // Aylık masrafları hesaplayan iş
    @Scheduled(cron = "0 0 0 1 * ?") // Her ayın ilk günü gece yarısı çalışır
    public void aggregateMonthlyExpenses() {
        aggregateExpenses(ChronoUnit.MONTHS);
    }

    private void aggregateExpenses(ChronoUnit chronoUnit) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = getStartTime(now, chronoUnit);
        List<User> users = userRepository.findAll();

        for (User user : users) {
            List<Transaction> transactions = transactionRepository.findAllByUserAndTransactionDateBetween(
                    user, startTime, now);
            BigDecimal total = transactions.stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            // Toplam masrafı kaydedin veya kullanıcıya bildirin
            // Örneğin: Kullanıcıya e-posta gönderme veya veritabanına kaydetme
            System.out.println("User: " + user.getFirstName() + " - Total " + chronoUnit.toString().toLowerCase() + " expense: " + total);
        }
    }

    private LocalDateTime getStartTime(LocalDateTime now, ChronoUnit chronoUnit) {
        switch (chronoUnit) {
            case DAYS:
                return now.minusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            case WEEKS:
                return now.minusWeeks(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            case MONTHS:
                return now.minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            default:
                throw new IllegalArgumentException("Unsupported ChronoUnit: " + chronoUnit);
        }
    }
}
