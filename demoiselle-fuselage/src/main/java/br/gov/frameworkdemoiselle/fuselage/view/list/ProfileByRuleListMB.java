package br.gov.frameworkdemoiselle.fuselage.view.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.enumeration.contrib.Comparison;
import br.gov.frameworkdemoiselle.enumeration.contrib.Logic;
import br.gov.frameworkdemoiselle.fuselage.business.ProfileByRuleBC;
import br.gov.frameworkdemoiselle.fuselage.configuration.FuselageConfig;
import br.gov.frameworkdemoiselle.fuselage.domain.SecurityProfileByRule;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.query.contrib.QueryConfig;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.contrib.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.contrib.Faces;
import br.gov.frameworkdemoiselle.util.contrib.Strings;

@ViewController
public class ProfileByRuleListMB extends AbstractListPageBean<SecurityProfileByRule, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private FuselageConfig viewConfig;

	@Inject
	private ProfileByRuleBC bc;

	@Override
	protected List<SecurityProfileByRule> handleResultList(QueryConfig<SecurityProfileByRule> queryConfig) {
		try {
			if (Strings.isNotBlank(getResultFilter())) {
				queryConfig.getFilter().put("name", getResultFilter());
				queryConfig.getFilter().put("description", getResultFilter());
				queryConfig.getFilter().put("implementation", getResultFilter());
				queryConfig.getFilter().put("keyname", getResultFilter());
				queryConfig.getFilter().put("value", getResultFilter());
				queryConfig.getFilter().put("valuenotation", getResultFilter());
				queryConfig.setFilterComparison(Comparison.CONTAINS);
				queryConfig.setFilterLogic(Logic.OR);
				queryConfig.setFilterCaseInsensitive(true);
			}
			if (!queryConfig.hasSorting())
				queryConfig.setSorting("name");
			return bc.findAll();
		} catch (RuntimeException e) {
			Faces.validationFailed();
			Faces.addI18nMessage("fuselage.generic.business.error", SeverityType.ERROR);
		}
		return new ArrayList<SecurityProfileByRule>();
	}

	@Transactional
	public String deleteSelection() {
		try {
			bc.delete(getSelectedList());
			initSelection();
		} catch (RuntimeException e) {
			Faces.validationFailed();
			Faces.addI18nMessage("fuselage.generic.business.error", SeverityType.ERROR);
		}
		return null;
	}

	public Map<String, String> getImplementations() {
		return viewConfig.getImplementations();
	}

}
