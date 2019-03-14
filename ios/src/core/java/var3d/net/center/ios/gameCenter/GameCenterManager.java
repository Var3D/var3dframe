package var3d.net.center.ios.gameCenter;

import org.robovm.apple.foundation.Foundation;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.foundation.NSErrorUserInfo;
import org.robovm.apple.gamekit.GKAchievement;
import org.robovm.apple.gamekit.GKGameCenterControllerDelegateAdapter;
import org.robovm.apple.gamekit.GKGameCenterViewController;
import org.robovm.apple.gamekit.GKGameCenterViewControllerState;
import org.robovm.apple.gamekit.GKLeaderboard;
import org.robovm.apple.gamekit.GKLocalPlayer;
import org.robovm.apple.gamekit.GKScore;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.objc.block.VoidBlock1;
import org.robovm.objc.block.VoidBlock2;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class GameCenterManager {
    public static final String GCM_DOMAIN = GameCenterManager.class.getSimpleName();
    public static final long GCM_ERROR_NOT_AUTHENTICATED = -1024;

    private static final int IOS_7 = 7;

    private final UIWindow keyWindow;
    private final GameCenterListener listener;

    public GameCenterManager(UIWindow keyWindow, GameCenterListener listener) {
        this.keyWindow = keyWindow;
        this.listener = listener;
    }


    public void login() {
        if (getIosVersion() >= IOS_7) {
            GKLocalPlayer.getLocalPlayer().setAuthenticateHandler(new VoidBlock2<UIViewController, NSError>() {
                @Override
                public void invoke(UIViewController viewController, NSError error) {
                    if (viewController != null) {
                        keyWindow.getRootViewController().presentViewController(viewController, true, null);
                    } else if (GKLocalPlayer.getLocalPlayer().isAuthenticated()) {
                        try {
                            listener.playerLoginCompleted(GKLocalPlayer.getLocalPlayer().getAlias());
                        } catch (Exception ex) {
                            listener.playerLoginCompleted("");
                        }
                    } else {
                        listener.playerLoginFailed(error);
                    }
                }
            });
        }
    }

    public void reportAchievement(String identifier) {
        reportAchievement(identifier, 100);
    }

    public void reportAchievement(String identifier, double percentComplete) {
        // If player is not authenticated, do nothing
        if (!GKLocalPlayer.getLocalPlayer().isAuthenticated()) {
            listener.achievementReportFailed(buildUnauthenticatedPlayerError());
            return;
        }

        GKAchievement achievement = new GKAchievement(identifier);
        achievement.setPercentComplete(percentComplete);
        achievement.setShowsCompletionBanner(true);

        NSArray<GKAchievement> achievements = new NSArray<GKAchievement>(achievement);

        GKAchievement.reportAchievements(achievements, new VoidBlock1<NSError>() {
            @Override
            public void invoke(NSError error) {
                if (error != null) {
                    listener.achievementReportFailed(error);
                } else {
                    listener.achievementReportCompleted();
                }
            }
        });
    }

    public void loadAchievements() {
        // If player is not authenticated, do nothing
        if (!GKLocalPlayer.getLocalPlayer().isAuthenticated()) {
            listener.achievementsLoadFailed(buildUnauthenticatedPlayerError());
            return;
        }

        GKAchievement.loadAchievements(new VoidBlock2<NSArray<GKAchievement>, NSError>() {
            @Override
            public void invoke(NSArray<GKAchievement> array, NSError error) {
                if (error != null) {
                    listener.achievementsLoadFailed(error);
                } else {
                    ArrayList<GKAchievement> achievements = new ArrayList<GKAchievement>();
                    for (GKAchievement achievement : array) {
                        achievements.add(achievement);
                    }
                    listener.achievementsLoadCompleted(achievements);
                }
            }
        });
    }

    public void resetAchievements() {
        // If player is not authenticated, do nothing
        if (!GKLocalPlayer.getLocalPlayer().isAuthenticated()) {
            listener.achievementsResetFailed(buildUnauthenticatedPlayerError());
            return;
        }

        GKAchievement.resetAchievements(new VoidBlock1<NSError>() {
            @Override
            public void invoke(NSError error) {
                if (error != null) {
                    listener.achievementsResetFailed(error);
                } else {
                    listener.achievementsResetCompleted();
                }
            }
        });
    }

    public void reportScore(String identifier, long score) {
        // If player is not authenticated, do nothing
        if (!GKLocalPlayer.getLocalPlayer().isAuthenticated()) {
            listener.scoreReportFailed(buildUnauthenticatedPlayerError());
            return;
        }

        GKScore scoreReporter = new GKScore();
        scoreReporter.setValue(score);

        // If iOS version is 7 or more we use the new method
        if (getIosVersion() >= IOS_7) {
            scoreReporter.setLeaderboardIdentifier(identifier);
            NSArray<GKScore> scores = new NSArray<GKScore>(scoreReporter);

            GKScore.reportScores(scores, new VoidBlock1<NSError>() {
                @Override
                public void invoke(NSError error) {
                    if (error != null) {
                        listener.scoreReportFailed(error);
                    } else {
                        listener.scoreReportCompleted();
                    }
                }
            });
        } else { // If iOS version is 6 or less we use the deprecated method
            scoreReporter.setCategory(identifier);
            scoreReporter.reportScore(new VoidBlock1<NSError>() {
                @Override
                public void invoke(NSError error) {
                    if (error != null) {
                        listener.scoreReportFailed(error);
                    } else {
                        listener.scoreReportCompleted();
                    }
                }
            });
        }
    }

    public void loadLeaderboards() {
        // If player is not authenticated, do nothing
        if (!GKLocalPlayer.getLocalPlayer().isAuthenticated()) {
            listener.leaderboardsLoadFailed(buildUnauthenticatedPlayerError());
            return;
        }

        GKLeaderboard.loadLeaderboards(new VoidBlock2<NSArray<GKLeaderboard>, NSError>() {
            @Override
            public void invoke(NSArray<GKLeaderboard> array, NSError error) {
                if (error != null) {
                    listener.leaderboardsLoadFailed(error);
                } else {
                    ArrayList<GKLeaderboard> leaderboards = new ArrayList<GKLeaderboard>();
                    for (GKLeaderboard leaderboard : array) {
                        leaderboards.add(leaderboard);
                    }
                    listener.leaderboardsLoadCompleted(leaderboards);
                }
            }
        });
    }

    public String getLeaderboardId(GKLeaderboard leaderboard) {
        if (getIosVersion() >= IOS_7) {
            return leaderboard.getIdentifier();
        } else {
            return leaderboard.getCategory();
        }
    }

    public void showAchievementsView() {
        // If player is not authenticated, do nothing
        if (!GKLocalPlayer.getLocalPlayer().isAuthenticated()) {
            return;
        }

        GKGameCenterViewController gameCenterView = new GKGameCenterViewController();
        gameCenterView.setGameCenterDelegate(new GKGameCenterControllerDelegateAdapter() {
            @Override
            public void didFinish(GKGameCenterViewController gameCenterViewController) {
                dismissViewControllerAndNotifyListener(gameCenterViewController, GKGameCenterViewControllerState.Achievements);
            }
        });
        gameCenterView.setViewState(GKGameCenterViewControllerState.Achievements);
        keyWindow.getRootViewController().presentViewController(gameCenterView, true, null);
    }

    public void show() {
        // If player is not authenticated, do nothing
        if (!GKLocalPlayer.getLocalPlayer().isAuthenticated()) {
            return;
        }

        GKGameCenterViewController gameCenterView = new GKGameCenterViewController();
        gameCenterView.setGameCenterDelegate(new GKGameCenterControllerDelegateAdapter() {
            @Override
            public void didFinish(GKGameCenterViewController gameCenterViewController) {
                dismissViewControllerAndNotifyListener(gameCenterViewController, GKGameCenterViewControllerState.Default);
            }
        });
        gameCenterView.setViewState(GKGameCenterViewControllerState.Default);
        keyWindow.getRootViewController().presentViewController(gameCenterView, true, null);
    }

    public void showLeaderboardsView() {
        // If player is not authenticated, do nothing
        if (!GKLocalPlayer.getLocalPlayer().isAuthenticated()) {
            return;
        }

        GKGameCenterViewController gameCenterView = new GKGameCenterViewController();
        gameCenterView.setGameCenterDelegate(new GKGameCenterControllerDelegateAdapter() {
            @Override
            public void didFinish(GKGameCenterViewController gameCenterViewController) {
                dismissViewControllerAndNotifyListener(gameCenterViewController, GKGameCenterViewControllerState.Leaderboards);
            }
        });
        gameCenterView.setViewState(GKGameCenterViewControllerState.Leaderboards);
        // gameCenterView.setLeaderboardIdentifier("CgkI4OvQqOcSEAIQBg");
        keyWindow.getRootViewController().presentViewController(gameCenterView, true, null);
    }

    public void showLeaderboardView(String identifier) {
        // If player is not authenticated, do nothing
        if (!GKLocalPlayer.getLocalPlayer().isAuthenticated()) {
            return;
        }

        GKGameCenterViewController gameCenterView = new GKGameCenterViewController();
        gameCenterView.setGameCenterDelegate(new GKGameCenterControllerDelegateAdapter() {
            @Override
            public void didFinish(GKGameCenterViewController gameCenterViewController) {
                dismissViewControllerAndNotifyListener(gameCenterViewController, GKGameCenterViewControllerState.Leaderboards);
            }
        });
        gameCenterView.setViewState(GKGameCenterViewControllerState.Leaderboards);
        if (getIosVersion() >= IOS_7)
            gameCenterView.setLeaderboardIdentifier(identifier);
        else
            gameCenterView.setLeaderboardCategory(identifier);

        keyWindow.getRootViewController().presentViewController(gameCenterView, true, null);
    }

    private void dismissViewControllerAndNotifyListener(UIViewController viewController,
                                                        final GKGameCenterViewControllerState viewControllerState) {
        viewController.dismissViewController(true, new Runnable() {
            @Override
            public void run() {
                switch (viewControllerState) {
                    case Achievements:
                        listener.achievementViewDismissed();
                        break;
                    case Leaderboards:
                        listener.leaderboardViewDismissed();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private int getIosVersion() {
        return Foundation.getMajorSystemVersion();
    }

    private NSError buildUnauthenticatedPlayerError() {
        NSErrorUserInfo info = new NSErrorUserInfo().setLocalizedDescription("Local player is unauthenticated");
        return new NSError(GCM_DOMAIN, GCM_ERROR_NOT_AUTHENTICATED, info);
    }

}