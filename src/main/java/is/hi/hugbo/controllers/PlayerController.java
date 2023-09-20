package is.hi.hugbo.controllers;

import is.hi.hugbo.repositories.PlayerRepository;
import is.hi.hugbo.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PlayerController {

  @Autowired
  PlayerRepository PR;

  /**
   * Returns all players in the database
   * 
   * @param name Optional name of player
   * @return List of all players in database with given name, if name empty
   *         returns all players
   */
  @GetMapping("/players")
  public ResponseEntity<List<Player>> getAllPlayers(@RequestParam(required = false) String name) {
    try {
      List<Player> players = new ArrayList<Player>();

      if (name == null)
        PR.findAll().forEach(players::add);
      else
        PR.findByName(name).forEach(players::add);

      if (players.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(players, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Returns player with given id
   * 
   * @param id Id of player
   * @return Player with given id
   */
  @GetMapping("/players/{id}")
  public ResponseEntity<Player> getPlayerById(@PathVariable("id") long id) {
    Optional<Player> playerData = PR.findById(id);

    if (playerData.isPresent()) {
      return new ResponseEntity<>(playerData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Creates new player in database
   * 
   * @param player Player to be created
   * @return Created player
   */
  @PostMapping("/players")
  public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
    try {
      Player tempPlayer = PR.save(new Player(player.getName(), player.getAge()));
      return new ResponseEntity<>(tempPlayer, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Updates player with given id
   * 
   * @param id     Id of player to be updated
   * @param player Player to be updated
   * @return Updated player
   */
  @PutMapping("/players/{id}")
  public ResponseEntity<Player> updateTutorial(@PathVariable("id") long id,
      @RequestBody Player player) {
    Optional<Player> playerData = PR.findById(id);

    if (playerData.isPresent()) {
      Player tempPlayer = playerData.get();
      tempPlayer.setName(player.getName());
      tempPlayer.setAge(player.getAge());
      return new ResponseEntity<>(PR.save(tempPlayer),
          HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Deletes player with given id
   * 
   * @param id Id of player to be deleted
   * @return Status of deletion
   */
  @DeleteMapping("/players/{id}")
  public ResponseEntity<HttpStatus> deletePlayer(@PathVariable("id") long id) {
    try {
      PR.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Deletes all players in database
   * 
   * @return Status of deletion
   */
  @DeleteMapping("/players")
  public ResponseEntity<HttpStatus> deleteAllPlayers() {
    try {
      PR.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  /**
   * Returns all players that are active
   *
   * @return List of all active players
   */
  @GetMapping("/players/active")
  public ResponseEntity<List<Player>> findAllActive(@RequestParam(required = false) Boolean status) {
    try {
      Boolean searchStatus = status == null ? true : status;
      List<Player> players = PR.findByActive(searchStatus);

      if (players.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(players, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Toggles the active status of a player
   *
   * @return Updated player
   */
  @PutMapping("/players/{id}/toggle")
  public ResponseEntity<Player> toggleActive(@PathVariable("id") long id) {
    Optional<Player> playerData = PR.findById(id);
    if (playerData.isPresent()) {
      Player tempPlayer = playerData.get();
      tempPlayer.toggleActive();
      return new ResponseEntity<>(PR.save(tempPlayer),
          HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
