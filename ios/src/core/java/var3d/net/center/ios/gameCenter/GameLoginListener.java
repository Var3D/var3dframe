package var3d.net.center.ios.gameCenter;

import org.robovm.apple.foundation.NSError;
import org.robovm.apple.gamekit.GKAchievement;
import org.robovm.apple.gamekit.GKLeaderboard;

import java.util.ArrayList;

/**
 * Created by fengyu on 2018/9/17.
 */

public class GameLoginListener implements GameCenterListener {
    @Override
    public void playerLoginCompleted(String playerName) {

    }

    @Override
    public void playerLoginFailed(NSError error) {

    }

    @Override
    public void achievementReportCompleted() {

    }

    @Override
    public void achievementReportFailed(NSError error) {

    }

    @Override
    public void achievementsLoadCompleted(ArrayList<GKAchievement> achievements) {

    }

    @Override
    public void achievementsLoadFailed(NSError error) {
    }

    @Override
    public void achievementsResetCompleted() {

    }

    @Override
    public void achievementsResetFailed(NSError error) {

    }

    @Override
    public void scoreReportCompleted() {

    }

    @Override
    public void scoreReportFailed(NSError error) {

    }

    @Override
    public void leaderboardsLoadCompleted(ArrayList<GKLeaderboard> scores) {

    }

    @Override
    public void leaderboardsLoadFailed(NSError error) {
    }

    @Override
    public void leaderboardViewDismissed() {

    }

    @Override
    public void achievementViewDismissed() {

    }
}
