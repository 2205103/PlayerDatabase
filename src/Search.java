import java.util.ArrayList;
import java.util.List;

//A database with features
public class Search extends PlayerList{

    List<Player> SearchByName(String name){
        List<Player> names = new ArrayList<>();
             for(Player p : player_list){
                   if(p.getName().equalsIgnoreCase(name)){
                        names.add(p);
                   }
            }
        return names;
    }

     List<Player> SearchByCountry(String country){
        List<Player> countryNames = new ArrayList<>();
             for(Player p : player_list){
                   if(p.getCountry().equalsIgnoreCase(country)){
                        countryNames.add(p);
                   }
            }

        return countryNames;
    }
    List<Player> SearchByClub(String club){
     List<Player> clubNames = new ArrayList<>();
          for(Player p : player_list){
                if(p.getClub().equalsIgnoreCase(club)){
                     clubNames.add(p);
                }
         }

     return clubNames;
    }

     List<Player> SearchByClub(String club,List<Player>player_list){
        List<Player> clubNames = new ArrayList<>();
             for(Player p : player_list){
                   if(p.getClub().equalsIgnoreCase(club)){
                        clubNames.add(p);
                   }
            }

        return clubNames;
    }
     List<Player> SearchByPosition(String position){
        List<Player> positions = new ArrayList<>();
             for(Player p : player_list){
                   if(p.getPosition().equalsIgnoreCase(position)){
                        positions.add(p);
                   }
            }

        return positions;
    }
     List<Player> SearchBySalary(double salary1,double salary2){
        List<Player> salaries = new ArrayList<>();
             for(Player p : player_list){
                   if(salary1 <= p.getSalary() && p.getSalary()<=salary2){
                        salaries.add(p);
                   }
            }

        return salaries;
    }
}
