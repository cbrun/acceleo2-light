package fr.obeo.acceleo.gen.ide;

import fr.obeo.acceleo.gen.template.scripts.SpecificScript;

public class JDTAwareSpecificScript extends SpecificScript {

    public JDTAwareSpecificScript() {
        this.scriptFinder = new JDTAwareScriptFinder();
    }

}
