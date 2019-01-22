/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.tests.arguments;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.github.nucleuspowered.nucleus.argumentparsers.NicknameArgument;
import io.github.nucleuspowered.nucleus.tests.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.ServiceManager;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Sponge.class)
public class NicknameArgumentTests extends TestBase {

    private static BiFunction<String, String, ArgumentParseException> ex = (a, b) -> new ArgumentParseException(Text.EMPTY, "", 0);

    @Test
    public void testWhenTwoPlayersWithTheSameNameAreInTheUserDatabaseOnlyAnExactMatchIsReturned() throws ArgumentParseException {
        Set<?> list = getParser().parseValue(mockSource(), "test", ex);

        Assert.assertEquals(1, list.size());
        Assert.assertEquals("test", ((User) list.iterator().next()).getName());
    }

    @Test
    public void testWhenTwoPlayersWithTheSameNameAreInTheUserDatabaseWithNoExactMatchReturnsBothIfTheyOtherwiseMatch() throws ArgumentParseException {
        Set<?> list = getParser().parseValue(mockSource(), "tes", ex);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testWhenTwoPlayersWithTheSameNameAreInTheUserDatabaseWithNoExactMatchReturnsOneReturnsIfOnlyOneMatches() throws ArgumentParseException {
        Set<?> list = getParser().parseValue(mockSource(), "testt", ex);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("testtest", ((User) list.iterator().next()).getName());
    }

    @Test(expected = ArgumentParseException.class)
    public void testWhenTwoPlayersWithTheSameNameAreInTheUserDatabaseWithNoExactMatchReturnsNoneReturnIfNoneMatch() throws Exception {
        Set<?> obj = getParser().parseValue(mockSource(), "blah", ex);
    }

    private NicknameArgument getParser() {
        // Setup the mock UserStorageService
        setupSpongeMock();

        // We're testing the UserParser
        return new NicknameArgument(Text.of("name"), NicknameArgument.Target.USER);
    }

    private void setupSpongeMock() {
        PowerMockito.mockStatic(Sponge.class);
        ServiceManager manager = Mockito.mock(ServiceManager.class);
        UserStorageService service = getMockUserStorageService();
        Server server = Mockito.mock(Server.class);
        Mockito.when(server.getPlayer(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(server.getOnlinePlayers()).thenReturn(ImmutableList.of());
        Mockito.when(Sponge.getServer()).thenReturn(server);
        Mockito.when(manager.provideUnchecked(UserStorageService.class)).thenReturn(service);
        Mockito.when(Sponge.getServiceManager()).thenReturn(manager);
    }

    private UserStorageService getMockUserStorageService() {
        GameProfile gp1 = Mockito.mock(GameProfile.class);
        GameProfile gp2 = Mockito.mock(GameProfile.class);
        Mockito.when(gp1.getName()).thenReturn(Optional.of("test"));
        Mockito.when(gp2.getName()).thenReturn(Optional.of("testtest"));

        UserStorageService mockUss = Mockito.mock(UserStorageService.class);
        Mockito.when(mockUss.getAll()).thenReturn(Lists.newArrayList(gp1, gp2));

        User u1 = Mockito.mock(User.class);
        Mockito.when(u1.getName()).thenAnswer(g -> gp1.getName().get());
        Mockito.when(u1.getPlayer()).thenAnswer(g -> Optional.empty());
        User u2 = Mockito.mock(User.class);
        Mockito.when(u2.getName()).thenAnswer(g -> gp2.getName().get());
        Mockito.when(u2.getPlayer()).thenAnswer(g -> Optional.empty());

        Mockito.when(mockUss.get(Mockito.any(GameProfile.class))).thenAnswer(invocation -> {
            GameProfile arg = invocation.getArgumentAt(0, GameProfile.class);
            if ("test".equalsIgnoreCase(arg.getName().orElse(null))) {
                return Optional.of(u1);
            } else if ("testtest".equalsIgnoreCase(arg.getName().orElse(null))) {
                return Optional.of(u2);
            }

            return Optional.empty();
        });

        Mockito.when(mockUss.match(Mockito.anyString())).thenAnswer(
                (Answer<Collection<GameProfile>>) invocation -> {
                    String arg = invocation.getArgumentAt(0, String.class);
                    List<GameProfile> profiles = new ArrayList<>();
                    if ("test".startsWith(arg.toLowerCase())) {
                        profiles.add(gp1);
                    }

                    if ("testtest".startsWith(arg.toLowerCase())) {
                        profiles.add(gp2);
                    }

                    return profiles;
                }
        );

        Mockito.when(mockUss.get(Mockito.anyString())).thenAnswer(
                (Answer<Optional<User>>) invocation -> {
                    String arg = (String) invocation.getArguments()[0];
                    if ("test".equalsIgnoreCase(arg)) {
                        return Optional.of(u1);
                    } else if ("testtest".equalsIgnoreCase(arg)) {
                        return Optional.of(u2);
                    }

                    return Optional.empty();
                }
        );
        return mockUss;
    }

    private CommandSource mockSource() {
        CommandSource mock = Mockito.mock(CommandSource.class);
        Mockito.when(mock.hasPermission(Mockito.any())).thenReturn(true);
        return mock;
    }
}
