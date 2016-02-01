package io.github.leonardosnt.testy;

import io.github.bktlib.command.*;
import io.github.bktlib.command.annotation.Command;
import io.github.bktlib.command.annotation.SubCommand;
import io.github.bktlib.command.args.CommandArgs;

@Command(
        name = "ez",
        usageTarget = UsageTarget.BOTH, 
        usage = "...",
        subCommands = { // ou " this::* " ou " this::[cmd,potion,kit,party,potions] "
                "this::[cmd, kit, points]"
        },
        description = "..."
)
public class EZCommands extends CommandBase
{
    @Override
    public CommandResult onExecute(CommandSource src, CommandArgs args)
    {
        getSubCommands().stream().map( CommandBase::getName ).forEach( src::sendMessage );
        
        return CommandResult.success();
    }

    @SubCommand( name = "cmd"  )
    CommandResult cmd( CommandSource src, CommandArgs args)
    {
        /*
             ez cmd -b <evento> <comando> = Bloquear um comando
             ez cmd -r <evento> <comando> = Remover um comando bloqueado
             ez cmd -c <evento> = Limpar comandos bloqueados
             ez cmd -l <evento> = Ver a lista de comandos bloqueados
         */
        return CommandResult.success();
    }

    @SubCommand( name = "potion"  )
    CommandResult potion( CommandSource src, CommandArgs args )
    {
        /*
             ez potion -b <evento> <id> = Bloquear uma poção no evento
             ez potion -r <evento> <id> = Remover uma poção bloqueada
             ez potion -c <evento> = Limpar pots bloqueados
             ez potion -l <evento> = Ver lista de pots bloqueadas
             ez potion -ids = Obter os IDs das poções do jogo
         */
        return CommandResult.success();
    }

    @SubCommand( name = "kit"  )
    CommandResult kit( CommandSource src, CommandArgs args )
    {
       /*
             ez kit -p <evento> <nome> = Permitir que um kit possa ser usado
             ez kit -b <evento> <nome> = Bloquear que um kit possa ser usado
             ez kit -l <evento> = Visualizar kits permitidos e bloqueados
             ez kit -c <evento> = Limpar dados de kits do evento
             ez kit -r <nome> = Remover um kit do plugin (apaga totalmente)
             ez kit price <valor> = Modifica o preço de um kit
             ez kit armor <valor> = Define a armadura do kit como a que você está usando
             ez kit contents <valor> = Define os itens do kit como os que você tem no inventário
        */
        return CommandResult.success();
    }

    @SubCommand( name = "party"  )
    CommandResult party( CommandSource src, CommandArgs args )
    {
        /*
             ez party -e <evento> = Ativa as partys no evento
             ez party -d <evento> = Desativa partys no evento
             ez party -l <evento> <quantia> = Define a quantia máxima de jogadores por party
         */
        return CommandResult.success();
    }

    @SubCommand( name = "points"  )
    CommandResult points( CommandSource src, CommandArgs args )
    {
        /*
             ez points add <player> <quantia> = Creditar pontos à um jogador
             ez points rem <player> <quantia> = Debitar pontos de um jogador
             ez points set <player> <quantia> = Modificar pontos de um jogador
             ez points coins <quantia> = Define quanto 01 ponto vale em coins
         */
        return CommandResult.success();
    }
}
