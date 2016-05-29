/*
 *  Copyright (C) 2016 Leonardosc
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

package io.github.bktlib.command.args;

import org.bukkit.entity.Player;

import io.github.bktlib.command.CommandResult;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Function;

/**
 * <p>
 * O intuito dessa classe facilitar a manipulção de argumentos passados ao
 * executar um {@link io.github.bktlib.command.CommandBase comando}
 * </p>
 * <p>
 * <p>
 * Os métodos {@code Unsafe*} podem retornar um valor nulo, ou lançar exceções,
 * use apenas se você tem certeza da precedência do argumento passado.
 * </p>
 * <p>
 * <p>
 * Todos os métodos que recebem argIndex como parametro podem lançar um
 * {@link ArrayIndexOutOfBoundsException} caso o numero de argumentos seja menor
 * ou igual ao numero passado.
 * <p>
 * <p>
 * <p>
 * Os comandos {@code TryGetAs*} podem lançar essa um
 * {@link NullPointerException} caso o parametro failCallback seja nulo.
 * </p>
 * <p>
 */
public interface CommandArgs {

  /**
   * @return Numero de argumentos presentes.
   */
  int size();

  /**
   * @return {@code true} caso não haja nenhum argumento, caso contrario
   * retorna {@code false}
   */
  boolean isEmpty();

  /**
   * @param argIndex Index o argumento
   * @return O argumento naquela posição.
   */
  String get(int argIndex);

  /**
   * @return Array de argumentos "crús"
   */
  String[] getRawArgs();

  /**
   * @param argIndex Index o argumento
   * @return Um optional que pode ou não conter o valor convertido.
   */
  OptionalInt getAsInt(int argIndex);

  /**
   * @param argIndex Index o argumento
   * @return Um optional que pode ou não conter o valor convertido.
   */
  OptionalDouble getAsDouble(int argIndex);

  /**
   * @param argIndex Index o argumento
   * @return Um optional que pode ou não conter o valor convertido.
   */
  Optional<Boolean> getAsBoolean(int argIndex);

  /**
   * @param argIndex Index o argumento
   * @return Um optional que pode ou não conter o valor convertido.
   */
  Optional<Player> getAsPlayer(int argIndex);

  /**
   * Tenta converter o argumento para {@code int}.
   * Caso não consiga ele executa o {@code failCallback}
   * passando como parametro o argumento.
   *
   * @param argIndex Index o argumento
   * @param failCallback Callback que sera executado.
   * @return O argumento convertido para {@code int}
   */
  int tryGetAsInt(int argIndex, Function<String, CommandResult> failCallback);

  /**
   * Tenta converter o argumento para {@code double}.
   * Caso não consiga ele executa o {@code failCallback}
   * passando como parametro o argumento.
   *
   * @param argIndex Index o argumento
   * @param failCallback Callback que sera executado.
   * @return O argumento convertido para {@code double}
   */
  double tryGetAsDouble(int argIndex, Function<String, CommandResult> failCallback);

  /**
   * Tenta converter o argumento para {@code boolean}.
   * Caso não consiga ele executa o {@code failCallback}
   * passando como parametro o argumento.
   *
   * @param argIndex Index o argumento
   * @param failCallback Callback que sera executado.
   * @return O argumento convertido para {@code boolean}
   */
  boolean tryGetAsBoolean(int argIndex, Function<String, CommandResult> failCallback);

  /**
   * Tentar encontrar o jogador correspondente ao nome informado
   * no argumento, Caso não encontre ele executa o {@code failCallback}
   * passando como parametro o argumento.
   *
   * @param argIndex Index o argumento
   * @param failCallback Callback que sera executado.
   * @return O jogador correspondente ao nome passado no argumento.
   */
  Player tryGetAsPlayer(int argIndex, Function<String, CommandResult> failCallback);

  /**
   * @param argIndex Index o argumento
   * @return O argumento convertido para {@code int}
   */
  int unsafeGetAsInt(int argIndex);

  /**
   * @param argIndex Index o argumento
   * @return O argumento convertido para {@code double}
   */
  double unsafeGetAsDouble(int argIndex);

  /**
   * @param argIndex Index o argumento
   * @return O argumento convertido para {@code boolean}
   */
  boolean unsafeGetAsBoolean(int argIndex);

  /**
   * @param argIndex Index o argumento
   * @return O jogador correspondente ao nome passado no argumento.
   */
  Player unsafeGetAsPlayer(int argIndex);

  static CommandArgs of(String... rawArgs) {
    return new CommandArgsImpl(rawArgs);
  }
}
