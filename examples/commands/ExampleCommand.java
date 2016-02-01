package io.github.leonardosnt.testy;

import io.github.bktlib.command.CommandBase;
import io.github.bktlib.command.CommandResult;
import io.github.bktlib.command.CommandSource;
import io.github.bktlib.command.UsageTarget;
import io.github.bktlib.command.annotation.Command;
import io.github.bktlib.command.annotation.SubCommand;
import io.github.bktlib.command.args.CommandArgs;
import io.github.bktlib.common.Strings;

@Command(
    name = "monei",
    usageTarget = UsageTarget.BOTH,
    usage = "",
    subCommands = {
            "this::*"
    },
    description = "Uma descricao bem legal"
)
public class ExampleCommand extends CommandBase
{
    @Override
    public CommandResult onExecute(final CommandSource src, final CommandArgs args )
    {
        getSubCommands().stream()
                .map( subCmd -> Strings.of( subCmd.getName(), " - ", subCmd.getDescription().orElse("Nenhuma descricao") ))
                .forEach( src::sendMessage );
        
        return CommandResult.success();
    }
    
    @SubCommand( name = "add")
    CommandResult subCommandAdd( CommandSource src, CommandArgs args )
    {
        if ( args.isEmpty() )
        {
            src.sendMessage("qtd");
        }
        else
        {
            double qtd = args.tryGetAsDouble(0, arg ->
                CommandResult.fail( "Numero invalido (%s)", arg )
            );

            src.sendMessage("Adicionado %s", qtd);
        }

        return CommandResult.success();
    }
    
    @SubCommand( name = "remove" )
    CommandResult subCommandRemove( CommandSource src, CommandArgs args )
    {
        return CommandResult.success();
    }
}
