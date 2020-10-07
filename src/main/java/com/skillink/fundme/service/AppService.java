package com.skillink.fundme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.skillink.fundme.dto.Dashboard;
import com.skillink.fundme.dto.DashboardItem;

@Service
public class AppService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Dashboard getDshboardStatistics() {
		Dashboard res = new Dashboard();
		
		String sql = "select (SELECT count(id) FROM tbl_campaign) as Campaigns, (SELECT count(id) FROM tbl_campaign where enabled = 1) as ActiveCampaigns, (SELECT count(id) FROM tbl_campaign where enabled = 0) as InactiveCampaigns, (select count(id) from tbl_contribution ) as Contributions, (select sum(amount) from tbl_contribution) as contributed, (select count(id) from tbl_user where role_id = 0 ) as beneficiaries";
		
		ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
		List<Map<String, Object>> list = jdbcTemplate.query(sql, rowMapper);

		Set<String> columns = null;
		for (Map<String, Object> map : list) {
			columns = map.keySet();
			break;
		}
		
		List<DashboardItem> items = new ArrayList<DashboardItem>();
		List<String> mainList = new ArrayList<String>();
		mainList.addAll(columns);
		
		for (int i = 0; i < list.size() ;i++) {
			
			Map<String, Object> map = list.get(i);
			int j = 0;
			for (String col : columns) {
				String name = mainList.get(j++);
				name = StringUtils.capitalize(name);
				name = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(name), ' ');
				//System.out.format("%n%s: %s", name, field.get(o));

				DashboardItem it = new DashboardItem();
				it.setName(name);
				if(map.get(col) != null)
					it.setValue(String.valueOf(map.get(col).toString()));
				items.add(it);
			}
			
		}
		res.setItems(items);
		
		return res;
	}
}
