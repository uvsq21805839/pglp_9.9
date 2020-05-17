package pglp_9.pglp_9.Command;

import pglp_9.pglp_9.figures.*;
import pglp_9.pglp_9.figures.interfaces.Figures;
import pglp_9.pglp_9.model.data.FigureCompositeDao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrawingTUI {
    private final Connection connection;

    public DrawingTUI(Connection connection){
        this.connection = connection;
    }

    public Command nextCommand(String input){
        String striped = input.replaceAll("\\s", "");
        Command addCommand = checkAjoutFigure(striped,TypeToken.RECTANGLE);
        boolean isAdded;
        if ( addCommand != null) {
            isAdded=true;
        }else {
          addCommand = checkAjoutFigure(striped,TypeToken.CERCLE);
            if ( addCommand != null) {
                isAdded = true;
            }else {
                addCommand = checkAjoutFigure(striped,TypeToken.CARREE);
                if ( addCommand != null) {
                    isAdded = true;
                }else {
                    addCommand = checkAjoutFigure(striped,TypeToken.TRIANGLE);
                    if ( addCommand != null) {
                        isAdded = true;
                    }else {
                        addCommand = checkAjoutFigure(striped,TypeToken.GROUPER);
                        isAdded =  addCommand != null;
                    }
                }
            }
        }
        if (isAdded)
            return addCommand;

        Command showCommand = checkAfficherFigure(striped);
        boolean isShown =showCommand != null;
        if (isShown)
            return showCommand;

        Command moveCommand = checkMoveFigure(striped);
        boolean isMoved =moveCommand != null;
        if (isMoved)
            return moveCommand;

        Command deleteCommand = checkDeleteFigure(striped);
        boolean isDeleted =deleteCommand != null;
        if (isDeleted)
            return deleteCommand;

        if (striped.toUpperCase().equals("AFFICHERTOUT()"))
            return new AfficherToutCommand(connection);

        if (striped.toUpperCase().equals("HELP") || striped.toUpperCase().equals("?"))
            return new HelpCommand();

        if (striped.toUpperCase().equals("EXIT") || striped.toUpperCase().equals("QUIT"))
            return new ExitCommand();

        return new NotFoundCommand();
    }

    public Command checkAfficherFigure(String input) {
        Pattern showFigurePattern = Pattern.compile("(\\w*)\\((\\w*)((,(\\w*))*?)\\)");
        Matcher showFigureMatcher = showFigurePattern.matcher(input);
        if (showFigureMatcher.matches()){
          if (TypeToken.AFFICHER.name().toUpperCase().equals(showFigureMatcher.group(1).toUpperCase())) {
              ArrayList<String> variables = new ArrayList<>();
              variables.add(showFigureMatcher.group(2));
              String[] splited = showFigureMatcher.group(3).split(",");
              for (String values:splited
              ) {
                  if (!values.trim().equals("")){
                      variables.add(values);
                  }
              }
              return new AfficherCommand(connection,variables);
          }
          return null;
        }
        return null;
    }

    public Command checkMoveFigure(String input) {
        Pattern moveFigurePattern = Pattern.compile("(\\w*)\\((\\w*),\\((\\d+),(\\d+)\\)\\)");
        Matcher moveFigureMatcher = moveFigurePattern.matcher(input);
        if (moveFigureMatcher.matches()){
            if (TypeToken.MOVE.name().toUpperCase().equals(moveFigureMatcher.group(1).toUpperCase())) {
                String variables =  moveFigureMatcher.group(2);
                int x = Integer.parseInt(moveFigureMatcher.group(3));
                int y = Integer.parseInt(moveFigureMatcher.group(4));
                return new MoveCommand(connection,variables,x,y);
            }
            return null;
        }
        return null;
    }

    public Command checkDeleteFigure(String input) {
        Pattern deleteFigurePattern = Pattern.compile("(\\w*)\\((\\w*)\\)");
        Matcher deleteFigureMatcher = deleteFigurePattern.matcher(input);
        if (deleteFigureMatcher.matches()){
            if (TypeToken.SUPPRIMER.name().toUpperCase().equals(deleteFigureMatcher.group(1).toUpperCase())) {
                String variables =  deleteFigureMatcher.group(2);
                return new DeleteCommand(connection,variables);
            }
            return null;
        }
        return null;
    }

    public Command checkAjoutFigure(String input,TypeToken token) {
        Pattern ajoutFigurePattern = Pattern.compile("(\\w*)=(\\w*)\\(\\((\\d+),(\\d+)\\),(\\d+(\\.\\d+)?)\\)");
        Matcher ajoutFigureMatcher = ajoutFigurePattern.matcher(input);
        if (token.equals(TypeToken.RECTANGLE)) {
            Pattern ajoutRectanglePattern = Pattern.compile("(\\w*)=(\\w*)\\(\\((\\d+),(\\d+)\\),(\\d+),(\\d+)\\)");
            ajoutFigureMatcher = ajoutRectanglePattern.matcher(input);
            if (ajoutFigureMatcher.matches()){
                if (token.name().toUpperCase().equals(ajoutFigureMatcher.group(2).toUpperCase())){
                    String variable = ajoutFigureMatcher.group(1);
                    int x = Integer.parseInt(ajoutFigureMatcher.group(3));
                    int y = Integer.parseInt(ajoutFigureMatcher.group(4));
                    int longueur = Integer.parseInt(ajoutFigureMatcher.group(5));
                    int largeur = Integer.parseInt(ajoutFigureMatcher.group(6));
                    Rectangle rectangle = new Rectangle(variable,longueur,largeur,x,y);
                    return new AjoutCommand(connection,rectangle);
                }
                return null;
            }
            return null;
        }else if (token.equals(TypeToken.CERCLE)){
            if (ajoutFigureMatcher.matches()){
                if (token.name().toUpperCase().equals(ajoutFigureMatcher.group(2).toUpperCase())){
                    String variable = ajoutFigureMatcher.group(1);
                    int x = Integer.parseInt(ajoutFigureMatcher.group(3));
                    int y = Integer.parseInt(ajoutFigureMatcher.group(4));
                    double rayon = Double.parseDouble(ajoutFigureMatcher.group(5));
                    Cercle cercle = new Cercle(variable,rayon,x,y);
                    return new AjoutCommand(connection,cercle);
                }
                return null;
            }
            return null;
        }else if (token.equals(TypeToken.CARREE)){
            if (ajoutFigureMatcher.matches()){
                if (token.name().toUpperCase().equals(ajoutFigureMatcher.group(2).toUpperCase())){
                    String variable = ajoutFigureMatcher.group(1);
                    int x = Integer.parseInt(ajoutFigureMatcher.group(3));
                    int y = Integer.parseInt(ajoutFigureMatcher.group(4));
                    double cote = Double.parseDouble(ajoutFigureMatcher.group(5));
                    Carree carree = new Carree(variable,cote,x,y);
                    return new AjoutCommand(connection,carree);
                }
                return null;
            }
            return null;
        }else if (token.equals(TypeToken.TRIANGLE)){
            if (ajoutFigureMatcher.matches()){
                if (token.name().toUpperCase().equals(ajoutFigureMatcher.group(2).toUpperCase())){
                    String variable = ajoutFigureMatcher.group(1);
                    int x = Integer.parseInt(ajoutFigureMatcher.group(3));
                    int y = Integer.parseInt(ajoutFigureMatcher.group(4));
                    double taille = Double.parseDouble(ajoutFigureMatcher.group(5));
                    Triangle triangle = new Triangle(variable,taille,x,y);
                    return new AjoutCommand(connection,triangle);
                }
                return null;
            }
            return null;
        }else if (token.equals(TypeToken.GROUPER)){
            ajoutFigurePattern = Pattern.compile("(\\w*)=(\\w*)\\((\\w*)((,(\\w*))+?)\\)");
            ajoutFigureMatcher = ajoutFigurePattern.matcher(input);
            if (ajoutFigureMatcher.matches()){
                if (token.name().toUpperCase().equals(ajoutFigureMatcher.group(2).toUpperCase())){
                    String variable = ajoutFigureMatcher.group(1);
                    ArrayList<String> variables = new ArrayList<>();
                    variables.add(ajoutFigureMatcher.group(3));
                    String[] splited = ajoutFigureMatcher.group(4).split(",");
                    for (String values:splited
                         ) {
                        if (!values.trim().equals("")){
                            variables.add(values);
                        }
                    }
                    FigureComposite figureComposite = new FigureComposite(variable);
                    Figures figures = null;
                    for (String value:variables){
                        figures = FigureCompositeDao.figureExist(connection,value);
                        if (figures==null){
                            System.out.println("la figure "+value+" n existe pas.");
                            break;
                        }
                        figureComposite.add(figures);
                    }
                    if (figures != null)
                        return new AjoutCommand(connection,figureComposite);
                    return null;
                }
                return null;
            }
            return null;
        }
        return null;
    }

}
