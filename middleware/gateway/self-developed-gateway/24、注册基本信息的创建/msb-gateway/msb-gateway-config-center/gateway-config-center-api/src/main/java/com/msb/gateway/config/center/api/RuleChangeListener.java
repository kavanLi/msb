package com.msb.gateway.config.center.api;

import com.msb.common.config.Rule;

import java.util.List;

public interface RuleChangeListener {

    void onRulesChange(List<Rule> rules );
}
