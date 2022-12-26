package com.ling1.springmvc.chip;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerStats;

@Service
public class ChipService {
	public static final List<Integer> safeParchisTiles = List.of(5, 12, 17, 22, 29, 34, 39, 46, 51, 56, 63, 68, 100);

	@Autowired
	private ChipRepository chipRepo;

	@Transactional
	public Chip save(Chip l) {
		return chipRepo.save(l);
	}

	public Chip findById(Integer id) {
		return chipRepo.findById(id).get();
	}

	public List<Chip> findChipInRel(Integer relPos, Match m) {
		List<Chip> chipsInRel = new ArrayList<>();
		for (PlayerStats ps : m.getPlayerStats()) {
			for (Chip c : ps.getChips()) {
				if (c.getRelativePosition() == relPos) {
					chipsInRel.add(c);
				}
			}
		}
		return chipsInRel;
	}

	public Integer barrierRebound(Integer jump, Match m, Chip c) {
		Integer i = 1;
		while (i <= jump) {
			if (c.getAbsolutePosition() + i > 63) {
				return jump;
			}
			Integer currentPos = c.getRelativePosition() + i;
			if (currentPos > 68) {
				currentPos = currentPos - 68;
			}
			if (findChipInRel(currentPos, m).size() == 2) {
				if (findChipInRel(currentPos, m).get(0).getChipColor() == findChipInRel(currentPos, m).get(1)
						.getChipColor() && findChipInRel(currentPos, m).get(0).getChipColor() != c.getChipColor()) {
							return i-1;
				}
			}
			i++;
		}
		if (findChipInRel(c.getRelativePosition() + jump, m).size() == 2) {
			return jump - 1;
		}
		return jump;
	}
}
