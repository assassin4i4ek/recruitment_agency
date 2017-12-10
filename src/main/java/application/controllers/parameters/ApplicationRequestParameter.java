package application.controllers.parameters;


public class ApplicationRequestParameter {
    private boolean edit = false;
    private boolean quantityError = false;
    private boolean finalizeError = false;

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isQuantityError() {
        return quantityError;
    }

    public void setQuantityError(boolean quantityError) {
        this.quantityError = quantityError;
    }

    public boolean isFinalizeError() {
        return finalizeError;
    }

    public void setFinalizeError(boolean finalizeError) {
        this.finalizeError = finalizeError;
    }
}