package mael;

/**
 * For exceptions specific to Mael
 */ 
class MaelException extends Exception {

    /**
     * Default constructor of Mael specific exceptions
     * 
     * @param message Error message accompanying the exception
     */
    public MaelException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "\tMael encountered a critical error...\n\t\t" 
                + super.getMessage() + "\n\n\t\t-Terminated request-";
    }
    
}

