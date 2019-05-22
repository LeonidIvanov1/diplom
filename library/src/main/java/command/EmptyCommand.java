package command;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand{
    @Override
    public ValueObject execute(HttpServletRequest request) {
        return null;
    }
}
