-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 28 sep. 2023 à 02:20
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `jiki-dev`
--

-- --------------------------------------------------------

--
-- Structure de la table `t_backlog`
--

CREATE TABLE `t_backlog` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `t_backlog`
--

INSERT INTO `t_backlog` (`id`, `title`, `description`, `status`, `creation_date`, `update_date`) VALUES
(2, 'Backlog Jiki', 'Backlog Jira like project', NULL, '2023-08-28 15:03:43', NULL),
(3, 'Backlog hiuiu', 'Backlog jfjhhjghj', NULL, '2023-09-02 12:54:01', NULL),
(4, 'Backlog hiuiu', 'Backlog jfjhhjghj', NULL, '2023-09-02 13:03:39', NULL),
(5, 'Backlog lkkjklj', 'Backlog jhgjkb', NULL, '2023-09-02 13:05:38', NULL),
(6, 'Backlog hfhgfq', 'Backlog khkjhk', NULL, '2023-09-02 13:08:32', NULL),
(7, 'Backlog hhf', 'Backlog HHFHF', NULL, '2023-09-02 13:09:30', NULL),
(8, 'Backlog GDGD', 'Backlog HGDGDGD', NULL, '2023-09-02 13:11:55', NULL),
(9, 'Backlog asd', 'Backlog adsd', NULL, '2023-09-03 09:47:40', NULL),
(10, 'Backlog fhgfq', 'Backlog jhghjg', NULL, '2023-09-03 10:28:42', NULL),
(11, 'Backlog hfgh', 'Backlog fhgfh', NULL, '2023-09-11 14:44:42', NULL),
(12, 'Backlog fsdf', 'Backlog fsdfsf', NULL, '2023-09-11 14:54:16', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `t_category`
--

CREATE TABLE `t_category` (
  `id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `t_config`
--

CREATE TABLE `t_config` (
  `secret_key` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `t_config`
--

INSERT INTO `t_config` (`secret_key`) VALUES
('@#$jikiAgile,;!');

-- --------------------------------------------------------

--
-- Structure de la table `t_project`
--

CREATE TABLE `t_project` (
  `id` int(11) NOT NULL,
  `backlog_id` int(11) DEFAULT NULL,
  `team_id` int(11) DEFAULT NULL,
  `shortname` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `t_project`
--

INSERT INTO `t_project` (`id`, `backlog_id`, `team_id`, `shortname`, `name`, `description`, `status`, `creation_date`, `end_date`, `update_date`) VALUES
(2, 2, 2, 'JK', 'Jiki', 'Jira like project', 'CREATED', '2023-08-28 15:03:43', NULL, '2023-09-03 10:26:10'),
(3, 9, 1, 'ADAAAAAA', 'asd', 'adsd', 'CREATED', '2023-09-03 09:47:40', NULL, '2023-09-05 16:40:30'),
(4, 11, 3, 'GFHF', 'hfgh', 'fhgfh', 'CREATED', '2023-09-11 14:44:42', NULL, NULL),
(5, 12, 4, 'FDS', 'fsdf', 'fsdfsf', 'CREATED', '2023-09-11 14:54:16', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `t_sprint`
--

CREATE TABLE `t_sprint` (
  `id` int(11) NOT NULL,
  `reporter_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `goal` text DEFAULT NULL,
  `duration` varchar(10) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `business_value` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `t_sprint`
--

INSERT INTO `t_sprint` (`id`, `reporter_id`, `project_id`, `title`, `name`, `goal`, `duration`, `description`, `business_value`, `start_date`, `end_date`, `status`, `creation_date`, `update_date`) VALUES
(2, 3, 2, 'kjkkjk', 'dfgdg', 'gfdg', '4W', 'hjhh', NULL, '2023-10-09 14:00:55', '2023-09-17 17:18:14', 'CLOSED', '2023-09-09 17:49:57', '2023-09-17 17:18:14'),
(3, 3, 2, 'dessd', NULL, NULL, NULL, 'sfdsfsf', NULL, NULL, NULL, 'CREATED', '2023-09-14 00:03:39', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `t_story`
--

CREATE TABLE `t_story` (
  `id` int(11) NOT NULL,
  `reporter_id` int(11) NOT NULL,
  `sprint_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `backlog_id` int(11) DEFAULT NULL,
  `assigned_team_id` int(11) DEFAULT NULL,
  `assigned_user_id` int(11) DEFAULT NULL,
  `short_title` varchar(50) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `scope` varchar(50) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `appli_version` varchar(255) DEFAULT NULL,
  `priority` varchar(10) DEFAULT NULL,
  `workflow` varchar(50) DEFAULT NULL,
  `business_value` int(10) DEFAULT NULL,
  `story_points` int(10) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `estimated_end_date` datetime DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `t_story`
--

INSERT INTO `t_story` (`id`, `reporter_id`, `sprint_id`, `project_id`, `backlog_id`, `assigned_team_id`, `assigned_user_id`, `short_title`, `title`, `description`, `type`, `scope`, `status`, `appli_version`, `priority`, `workflow`, `business_value`, `story_points`, `start_date`, `end_date`, `estimated_end_date`, `creation_date`, `update_date`) VALUES
(3, 3, 3, 2, NULL, NULL, NULL, 'JK-3', 'fsdf', NULL, 'Bug', '', 'TODO', NULL, 'Major', NULL, NULL, 1, NULL, NULL, NULL, '2023-09-07 23:21:11', '2023-09-27 19:27:21'),
(4, 3, NULL, 2, 2, NULL, NULL, 'JK-4', 'Story test', NULL, 'Bug', 'BACKEND', 'IN_PROGRESS', NULL, 'MINOR', NULL, NULL, 13, NULL, NULL, NULL, '2023-09-07 23:21:31', '2023-09-27 19:27:32'),
(5, 3, 3, 2, NULL, NULL, 3, 'JK-5', 'adsad', '<p>asdad</p>', 'USER_STORY', 'FRONTEND', 'TODO', NULL, 'CRITICAL', NULL, NULL, 1, NULL, NULL, NULL, '2023-09-08 09:58:09', '2023-09-27 18:20:22'),
(6, 3, NULL, 2, 2, NULL, 0, 'JK-6', 'test', NULL, 'BUG', 'FRONTEND', 'TODO', NULL, 'MAJOR', NULL, NULL, 2, NULL, NULL, NULL, '2023-09-08 10:30:23', '2023-09-27 02:26:30'),
(7, 3, 3, 2, NULL, NULL, NULL, 'JK-7', 'koiokop', NULL, 'USER_STORY', '', 'IN_PROGRESS', NULL, 'CRITICAL', NULL, NULL, 3, NULL, NULL, NULL, '2023-09-08 10:59:13', '2023-09-27 19:27:27'),
(8, 3, 2, 2, NULL, NULL, NULL, 'JK-8', 'adsad', NULL, 'BUG', '', 'DONE', NULL, 'MINOR', NULL, NULL, 13, NULL, NULL, NULL, '2023-09-08 12:34:11', '2023-09-16 14:51:40'),
(9, 3, NULL, 2, 2, NULL, NULL, 'JK-9', 'asd', NULL, 'BUG', '', 'BLOCKED', NULL, 'MAJOR', NULL, NULL, 8, NULL, NULL, NULL, '2023-09-08 12:35:12', '2023-09-18 01:42:52'),
(10, 3, NULL, 2, 2, NULL, NULL, 'JK-10', 'fdsf', NULL, 'BUG', '', 'BLOCKED', NULL, 'MAJOR', NULL, NULL, 8, NULL, NULL, NULL, '2023-09-08 12:36:05', '2023-09-18 01:42:51'),
(11, 3, NULL, 2, 2, NULL, NULL, 'JK-11', 'fsdf', NULL, 'USER_STORY', '', 'BLOCKED', NULL, 'MINOR', NULL, NULL, 8, NULL, NULL, NULL, '2023-09-08 12:46:18', '2023-09-18 01:42:53'),
(12, 3, NULL, 2, 2, NULL, NULL, 'JK-12', 'dsad', NULL, 'USER_STORY', '', 'IN_PROGRESS', NULL, 'MAJOR', NULL, NULL, 3, NULL, NULL, NULL, '2023-09-08 12:48:32', '2023-09-17 21:10:07'),
(13, 3, NULL, 2, 2, NULL, NULL, 'JK-13', 'ads', NULL, 'USER_STORY', '', 'IN_PROGRESS', NULL, 'MAJOR', NULL, NULL, 3, NULL, NULL, NULL, '2023-09-08 12:48:47', NULL),
(14, 3, NULL, 2, 2, NULL, NULL, 'JK-14', 'fsdf', NULL, 'USER_STORY', '', 'IN_PROGRESS', NULL, 'MAJOR', NULL, NULL, 2, NULL, NULL, NULL, '2023-09-08 12:53:52', '2023-09-14 14:40:02'),
(15, 3, NULL, 2, 2, NULL, NULL, 'JK-15', 'sfdf', NULL, 'BUG', '', 'IN_PROGRESS', NULL, 'CRITICAL', NULL, NULL, 3, NULL, NULL, NULL, '2023-09-09 00:28:36', '2023-09-14 14:40:04'),
(16, 3, 2, 2, NULL, NULL, NULL, 'JK-16', 'adsad', NULL, 'BUG', 'BACKEND', 'TODO', NULL, 'CRITICAL', NULL, NULL, 5, NULL, NULL, NULL, '2023-09-26 22:38:27', NULL),
(17, 3, NULL, 2, 2, NULL, NULL, 'JK-17', 'RTRET', '<p>SDFSF</p>', 'USER_STORY', 'FRONTEND', 'IN_PROGRESS', NULL, 'MAJOR', NULL, NULL, 8, NULL, NULL, NULL, '2023-09-26 22:43:26', '2023-09-27 18:20:25');

-- --------------------------------------------------------

--
-- Structure de la table `t_team`
--

CREATE TABLE `t_team` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(50) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `t_team`
--

INSERT INTO `t_team` (`id`, `name`, `description`, `status`, `creation_date`, `update_date`) VALUES
(1, 'Administrateur', '', 'CREATED', '2023-08-25 14:50:53', NULL),
(2, 'Agile', '', 'CREATED', '2023-08-28 09:28:21', NULL),
(3, 'dasd', 'asdad', 'CREATED', '2023-09-08 12:11:40', NULL),
(4, 'qwqwqw', 'dfgdgd', 'CREATED', '2023-09-08 12:25:19', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `t_user`
--

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `jobtitle` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `t_user`
--

INSERT INTO `t_user` (`id`, `team_id`, `firstname`, `lastname`, `email`, `username`, `password`, `jobtitle`, `role`, `status`, `creation_date`, `update_date`) VALUES
(1, 1, 'Mohamed', 'FOFANA', 'fofana.mansour@gmail.com', 'mfofana', '$2a$10$gZUb5Wkarft5G/XSSJjEZuA0QSswxffm9amwbfbkp.mz7D0njwjUa', 'SOFT_DEV', 'ADMIN', 'ACTIVE', '2023-07-18 16:26:11', '2023-09-26 21:25:30'),
(2, 2, 'TESTEUR', 'TEST', 'test@hotmail.com', 'testeur', '$2a$10$xNbAQJZ1wUudGzHcKi0fsuFc9BbX2F7.Bg1YLdx.gEld.VRJG0.VS', 'QA', 'USER', 'ACTIVE', '2023-08-28 17:35:28', '2023-08-28 17:36:33'),
(3, 2, 'Pierre', 'Dupont', 'pierre.dupont@hotmail.com', 'pdupont', '$2a$10$QCpj/EcQg8z35wJEyaPQQ.G4Qigk1eLp1dx.WtoF2/MFpvGYtOg8u', 'MANAGER', 'USER', 'ACTIVE', '2023-08-28 19:02:13', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `t_version`
--

CREATE TABLE `t_version` (
  `id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `version` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `t_backlog`
--
ALTER TABLE `t_backlog`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_category`
--
ALTER TABLE `t_category`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_project`
--
ALTER TABLE `t_project`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_sprint`
--
ALTER TABLE `t_sprint`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_story`
--
ALTER TABLE `t_story`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_team`
--
ALTER TABLE `t_team`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_user`
--
ALTER TABLE `t_user`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_version`
--
ALTER TABLE `t_version`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `t_backlog`
--
ALTER TABLE `t_backlog`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `t_category`
--
ALTER TABLE `t_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `t_project`
--
ALTER TABLE `t_project`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `t_sprint`
--
ALTER TABLE `t_sprint`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `t_story`
--
ALTER TABLE `t_story`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT pour la table `t_team`
--
ALTER TABLE `t_team`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT pour la table `t_user`
--
ALTER TABLE `t_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT pour la table `t_version`
--
ALTER TABLE `t_version`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
